/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */

#include "sql/stmt/select_stmt.h"
#include "rc.h"
#include "sql/parser/parse_defs.h"
#include "sql/stmt/filter_stmt.h"
#include "common/log/log.h"
#include "common/lang/string.h"
#include "storage/common/db.h"
#include "storage/common/table.h"
#include <unordered_set>

#include "util/util.h"

SelectStmt::~SelectStmt()
{
  if (nullptr != filter_stmt_) {
    delete filter_stmt_;
    filter_stmt_ = nullptr;
  }
}

static void wildcard_fields(Table *table, std::vector<Field> &field_metas)
{
  const TableMeta &table_meta = table->table_meta();
  const int start_idx = table_meta.sys_field_num();
  const int field_cnt = table_meta.field_num();
  for (int i = start_idx; i < field_cnt; i++) {
    field_metas.push_back(Field(table, table_meta.field(i)));
  }
}

RC SelectStmt::create(Db *db, Selects &select_sql, Stmt *&stmt, std::vector<std::pair<std::string, Table*>> *parent_tables)
{

  if (nullptr == db) {
    LOG_WARN("invalid argument. db is null");
    return RC::INVALID_ARGUMENT;
  }

  // select的值为空
  if (select_sql.attr_num == 0 && select_sql.aggregation_attr_num == 0) return RC::GENERIC_ERROR;
  // 非group-by无法使用聚合函数
  if (select_sql.is_group == 0 && select_sql.attr_num != 0 && select_sql.aggregation_attr_num != 0) return RC::GENERIC_ERROR;
  // group-by必须搭配聚合
  if (select_sql.is_group == 1 && select_sql.aggregation_attr_num == 0) return RC::GENERIC_ERROR;


  RC rc = RC::SUCCESS;
  bool order = false;
  if (select_sql.is_order == 1) order = true;

  // collect tables in `from` statement
  std::vector<Table *> tables;
  std::unordered_map<std::string, Table *> table_map;

  if (parent_tables != nullptr) {
    // add the parent tables into the table map(when sub query)
    for (auto &table: *parent_tables) {
      table_map.insert(table);
    }
  }
  
  std::unordered_set<std::string> alias_set_in_this_level;
  // add string2table for alias
  for (size_t i = 0; i < select_sql.relation_num; ++i) {
    const char *table_name = select_sql.relations[i];
    if (nullptr == table_name) {
      break;
      LOG_WARN("invalid argument. relation name is null. index=%d", i);
      return RC::INVALID_ARGUMENT;
    }

    Table *table = db->find_table(table_name);
    if (nullptr == table) {
      LOG_WARN("no such table. db=%s, table_name=%s", db->name(), table_name);
      return RC::SCHEMA_TABLE_NOT_EXIST;
    }

    tables.push_back(table);
    table_map.insert(std::pair<std::string, Table*>(table_name, table));
    const char *table_alias = select_sql.relation_alias[i];
    if (!common::is_blank(table_alias)) {
      if (alias_set_in_this_level.find(std::string(table_alias)) != alias_set_in_this_level.end()) {
        return RC::SCHEMA_TABLE_NAME_ILLEGAL;
      }
      alias_set_in_this_level.emplace(std::string(table_alias));
      const_cast<TableMeta&>(table->table_meta()).set_alias(table_alias);
      table_map.insert(std::pair<std::string, Table*>(table_alias, table));
    }
  }

  
  // collect query fields in `select` statement
  std::vector<Field> query_fields;
  int pvt = -1;
  for (int i = select_sql.attr_num - 1; i >= 0; --i) {
    
    const RelAttr &relation_attr = select_sql.attributes[i];

    if (select_sql.func_op_list[i] != NO_FUNCTION_OP && pvt == -1) {
      pvt = i;
    } else if (select_sql.func_op_list[i] == NO_FUNCTION_OP && pvt != -1) {
      for (int left = i - 1, right = pvt; left < right; ++left, --right) {
        Field tmp1 = query_fields[select_sql.attr_num - 1 - left];
        query_fields[select_sql.attr_num - 1 - left] = query_fields[select_sql.attr_num - 1 - right];
        query_fields[select_sql.attr_num - 1 - right] = tmp1;

        FuncOp tmp2 = select_sql.func_op_list[left];
        select_sql.func_op_list[left] = select_sql.func_op_list[right];
        select_sql.func_op_list[right] = tmp2;

        Value tmp3 = select_sql.func_value_list1[left];
        select_sql.func_value_list1[left] = select_sql.func_value_list1[right];
        select_sql.func_value_list1[right] = tmp3;

        Value tmp4 = select_sql.func_value_list2[left];
        select_sql.func_value_list2[left] = select_sql.func_value_list2[right];
        select_sql.func_value_list2[right] = tmp4;
      }
      pvt == -1;
    }

    
    if (select_sql.func_op_list[i] != NO_FUNCTION_OP && select_sql.func_value_list1[i].type != UNDEFINED) {
      FieldMeta *field_meta_1 = new FieldMeta;

      rc = field_meta_1->init("const", select_sql.func_value_list1[i].type, -1, -1, true, false);
      if (rc != RC::SUCCESS) return RC::MISS_TYPE;

      field_meta_1->set_alias(relation_attr.alias_name);
      query_fields.push_back(Field(nullptr, field_meta_1));
    }
    else if (common::is_blank(relation_attr.relation_name) && 0 == strcmp(relation_attr.attribute_name, "*")) {
      // select * from t1, t2
      if (nullptr != relation_attr.alias_name) {
        LOG_WARN("should give an alias to *");
        return RC::GENERIC_ERROR;
      }

      if (select_sql.aggregation_num > 0) {
        if (select_sql.aggregation_ops[i] != COUNT_OP) return RC::GENERIC_ERROR;
        query_fields.push_back(Field());
      } else {
        for (Table *table : tables) {
          wildcard_fields(table, query_fields);
        }
      }
    } 
    else if (!common::is_blank(relation_attr.relation_name)) {
      // select t1.id from t1, t2
      // select t1.* from t1, t2
      const char *alias_name = relation_attr.alias_name;
      const char *field_name = relation_attr.attribute_name;
      const char *table_name = relation_attr.relation_name;

      if (0 == strcmp(table_name, "*")) {
        if (0 != strcmp(field_name, "*")) {
          LOG_WARN("invalid field name while table is *. attr=%s", field_name);
          return RC::SCHEMA_FIELD_MISSING;
        }
        for (Table *table : tables) {
          wildcard_fields(table, query_fields);
        }
      } else {
        auto iter = table_map.find(table_name);
        if (iter == table_map.end()) {
          LOG_WARN("no such table in from list: %s", table_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        Table *table = iter->second;
        if (0 == strcmp(field_name, "*")) {
          if (alias_name) {
            LOG_WARN("shouldn't give an alias to *");
            return RC::GENERIC_ERROR;
          }
          wildcard_fields(table, query_fields);
        } else {
          FieldMeta *field_meta = const_cast<FieldMeta*>(table->table_meta().field(field_name));
          if (nullptr == field_meta) {
            LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), field_name);
            return RC::SCHEMA_FIELD_MISSING;
          }
          if (alias_name != nullptr) {
            field_meta->set_alias(std::string(alias_name));
          }
          query_fields.push_back(Field(table, field_meta));
        }
      }
    }
    else {
      // select id from t1
      if (tables.size() != 1) {
        LOG_WARN("invalid. I do not know the attr's table. attr=%s", relation_attr.attribute_name);
        return RC::SCHEMA_FIELD_MISSING;
      }

      Table *table = tables[0];
      FieldMeta *field_meta = const_cast<FieldMeta*>(table->table_meta().field(relation_attr.attribute_name));
      if (nullptr == field_meta) {
        LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), relation_attr.attribute_name);
        return RC::SCHEMA_FIELD_MISSING;
      }

      const char *alias_name = relation_attr.alias_name;
      if (nullptr != alias_name) {
        field_meta->set_alias(std::string(alias_name));
      }
      query_fields.push_back(Field(table, field_meta));
    }
  }

  if (pvt != -1) {
      for (int left = 0, right = pvt; left < right; ++left, --right) {
        Field tmp1 = query_fields[select_sql.attr_num - 1 - left];
        query_fields[select_sql.attr_num - 1 - left] = query_fields[select_sql.attr_num - 1 - right];
        query_fields[select_sql.attr_num - 1 - right] = tmp1;

        FuncOp tmp2 = select_sql.func_op_list[left];
        select_sql.func_op_list[left] = select_sql.func_op_list[right];
        select_sql.func_op_list[right] = tmp2;

        Value tmp3 = select_sql.func_value_list1[left];
        select_sql.func_value_list1[left] = select_sql.func_value_list1[right];
        select_sql.func_value_list1[right] = tmp3;

        Value tmp4 = select_sql.func_value_list2[left];
        select_sql.func_value_list2[left] = select_sql.func_value_list2[right];
        select_sql.func_value_list2[right] = tmp4;
      }
    }
    
  // collect order fields in `order` statement
  std::vector<std::pair<Field, bool>> order_fields;
  if (order) {
    for (int i = select_sql.order_attr_num - 1; i >= 0; --i) {
      const RelAttr &relation_attr = select_sql.order_attrs[i];
      bool asc = false;
      if (select_sql.is_asc[i] == 1) asc = true;
      if (0 == strcmp(relation_attr.attribute_name, "*")) {
        return RC::GENERIC_ERROR;
      } else if (!common::is_blank(relation_attr.relation_name)) {
        // order by t1.id
        const char *field_name = relation_attr.attribute_name;
        const char *table_name = relation_attr.relation_name;
        auto iter = table_map.find(table_name);
        if (iter == table_map.end()) {
          LOG_WARN("no such table in from list: %s", table_name);
          return RC::SCHEMA_FIELD_MISSING;
        }
        Table *table = iter->second;
        const FieldMeta *field_meta = table->table_meta().field(field_name);
        if (nullptr == field_meta) {
          LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), field_name);
          return RC::SCHEMA_FIELD_MISSING;
        }
        order_fields.push_back(std::make_pair(Field(table, field_meta), asc));
      } else {
        // order by id
        if (tables.size() != 1) {
          LOG_WARN("invalid. I do not know the attr's table. attr=%s", relation_attr.attribute_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        Table *table = tables[0];
        const FieldMeta *field_meta = table->table_meta().field(relation_attr.attribute_name);
        if (nullptr == field_meta) {
          LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), relation_attr.attribute_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        order_fields.push_back(std::make_pair(Field(table, field_meta), asc));
      }
    }

  }

  std::vector<Field> group_fields;
  bool group = (select_sql.is_group == 1);
  if (group) {
    for (int i = select_sql.group_attr_num - 1; i >= 0; i--) {
      const RelAttr &relation_attr = select_sql.group_attrs[i];

      if (!common::is_blank(relation_attr.relation_name)) {
        //  t1.id
        const char *field_name = relation_attr.attribute_name;
        const char *table_name = relation_attr.relation_name;

        auto iter = table_map.find(table_name);
        if (iter == table_map.end()) {
          LOG_WARN("no such table in from list: %s", table_name);
          return RC::SCHEMA_FIELD_MISSING;
        }
        Table *table = iter->second;
        const FieldMeta *field_meta = table->table_meta().field(field_name);
        if (nullptr == field_meta) {
          LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), field_name);
          return RC::SCHEMA_FIELD_MISSING;
        }
        group_fields.push_back(Field(table, field_meta));
      } else {
        // id 
        if (tables.size() != 1) {
          LOG_WARN("invalid. I do not know the attr's table. attr=%s", relation_attr.attribute_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        Table *table = tables[0];
        const FieldMeta *field_meta = table->table_meta().field(relation_attr.attribute_name);
        if (nullptr == field_meta) {
          LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), relation_attr.attribute_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        group_fields.push_back(Field(table, field_meta));
      }
    }
    // 检查 query_field 是否为group_fields的子集
    for (const Field &query_field : query_fields) {
      bool valid = false;
      for (const Field &group_field : group_fields) {
        if (0 == strcmp(query_field.table_name(), group_field.table_name())
            && 0 == strcmp(query_field.field_name(), group_field.field_name())) {
              valid = true;
        }
      }
      if (!valid) {
        return RC::GENERIC_ERROR;
      }
    }
  }
  if (select_sql.aggregation_num > 0 && select_sql.aggregation_num != select_sql.aggregation_attr_num) {
    // printf("aggregation_num_is %d but aggregation_attr_num is %d\n", select_sql.aggregation_num, select_sql.aggregation_attr_num);
    return RC::GENERIC_ERROR;
  }

  std::vector<Field> aggr_fields;
  for (int i = select_sql.aggregation_attr_num - 1; i >= 0; --i) {
    const RelAttr &relation_attr = select_sql.aggregation_attrs[i];
    if (common::is_blank(relation_attr.relation_name) && 0 == strcmp(relation_attr.attribute_name, "*")) {
      // select count(*)
      if (select_sql.aggregation_num > 0) {
        if (select_sql.aggregation_ops[i] != COUNT_OP) {
          return RC::GENERIC_ERROR;
        }

        aggr_fields.push_back(Field());
      }
    } else if (!common::is_blank(relation_attr.relation_name)) {
      // select t1.id from t1, t2
      // select t1.* from t1, t2
      const char *field_name = relation_attr.attribute_name;
      const char *table_name = relation_attr.relation_name;

      if (0 == strcmp(table_name, "*")) {
        printf("aggr on *.id / *.*\n");
        return RC::SCHEMA_FIELD_MISSING;
      } else {
        auto iter = table_map.find(table_name);
        if (iter == table_map.end()) {
          LOG_WARN("no such table in from list: %s", table_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        Table *table = iter->second;
        if (0 == strcmp(field_name, "*")) {
          // wildcard_fields(table, query_fields);
          if (select_sql.aggregation_ops[i] == COUNT_OP) {
            aggr_fields.push_back(Field(table, nullptr));
          }
        } else {
          const FieldMeta *field_meta = table->table_meta().field(field_name);
          if (nullptr == field_meta) {
            LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), field_name);
            return RC::SCHEMA_FIELD_MISSING;
          }

          aggr_fields.push_back(Field(table, field_meta));
        }
      }
    } else {
      // select id from t1
      if (tables.size() != 1) {
        LOG_WARN("invalid. I do not know the attr's table. attr=%s", relation_attr.attribute_name);
        return RC::SCHEMA_FIELD_MISSING;
      }

      Table *table = tables[0];
      const FieldMeta *field_meta = table->table_meta().field(relation_attr.attribute_name);
      if (nullptr == field_meta) {
        LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), relation_attr.attribute_name);
        return RC::SCHEMA_FIELD_MISSING;
      }

      aggr_fields.push_back(Field(table, field_meta));
    }
  }


  LOG_INFO("got %d tables in from stmt and %d fields in query stmt", tables.size(), query_fields.size());

  Table *default_table = nullptr;
  if (tables.size() == 1) {
    default_table = tables[0];
  }

  // create filter statement in `where` statement
  FilterStmt *filter_stmt = nullptr;
  rc = FilterStmt::create(db, default_table, &table_map,
           select_sql.conditions, select_sql.condition_num, filter_stmt);
  if (rc != RC::SUCCESS) {
    LOG_WARN("cannot construct filter stmt");
    return rc;
  }

    
  size_t n = select_sql.aggregation_num;
  std::vector<size_t> aggr_op_index(n);
  std::vector<AggrOp> aggregation_ops(n);
  std::vector<char *> aggregation_alias(n);
  auto sum = query_fields.size() + n;
  for (int i = 0; i < n; ++i) {
    aggregation_alias[i] = select_sql.aggregation_alias[i];
    aggr_op_index[i] = sum - select_sql.aggr_op_index[n - i - 1] - 1;
    aggregation_ops[i] = select_sql.aggregation_ops[i];
  }

  std::vector<AggrOp> having_ops;
  for (int i = 0; i < select_sql.having_condition_num; ++i) {
    having_ops.push_back(select_sql.having_conditions[i].aggr);
  }
  bool having = (select_sql.is_having == 1);
  std::vector<Field> having_fields;
  std::vector<HavingFilter> having_filters;
  if (having) {
    for (int i = 0; i < select_sql.having_condition_num; ++i) {
      HavingFilter filter;
      HavingCondition condition = select_sql.having_conditions[i];
      filter.right_value = condition.value;
      filter.cmp_op = condition.cmp_op;
      having_filters.push_back(filter);
    for (int i = select_sql.having_condition_num - 1; i >= 0; --i) {
      const RelAttr &relation_attr = select_sql.having_conditions[i].attr;
      if (common::is_blank(relation_attr.relation_name) && 0 == strcmp(relation_attr.attribute_name, "*")) {
        if (select_sql.having_conditions[i].aggr != COUNT_OP) return RC::GENERIC_ERROR;
        having_fields.push_back(Field());
      } else if (!common::is_blank(relation_attr.relation_name)) {
        const char *field_name = relation_attr.attribute_name;
        const char *table_name = relation_attr.relation_name;
        if (0 == strcmp(table_name, "*")) {
          // printf("aggr on *.id / *.*\n");
          return RC::SCHEMA_FIELD_MISSING;
        } else {
          auto iter = table_map.find(table_name);
          if (iter == table_map.end()) {
            LOG_WARN("no such table in from list: %s", table_name);
            return RC::SCHEMA_FIELD_MISSING;
          }

          Table *table = iter->second;
          if (0 == strcmp(field_name, "*")) {
            if (select_sql.aggregation_ops[i] == COUNT_OP) {
              having_fields.push_back(Field(table, nullptr));
            }
          } else {
            const FieldMeta *field_meta = table->table_meta().field(field_name);
            if (nullptr == field_meta) {
              LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), field_name);
              return RC::SCHEMA_FIELD_MISSING;
            }

            having_fields.push_back(Field(table, field_meta));
          }
        }
      } else {
        // id
        if (tables.size() != 1) {
          LOG_WARN("invalid. I do not know the attr's table. attr=%s", relation_attr.attribute_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        Table *table = tables[0];
        const FieldMeta *field_meta = table->table_meta().field(relation_attr.attribute_name);
        if (nullptr == field_meta) {
          LOG_WARN("no such field. field=%s.%s.%s", db->name(), table->name(), relation_attr.attribute_name);
          return RC::SCHEMA_FIELD_MISSING;
        }

        having_fields.push_back(Field(table, field_meta));
      }
    }
    }
  }

  // everything alright
  SelectStmt *select_stmt = new SelectStmt();
  select_stmt->tables_.swap(tables);
  select_stmt->query_fields_.swap(query_fields);
  select_stmt->filter_stmt_ = filter_stmt;
  select_stmt->aggregation_ops_ = aggregation_ops;
  select_stmt->aggregation_alias_ = aggregation_alias;
  stmt = select_stmt;
  select_stmt->aggrops_idx_in_fields_ = aggr_op_index;
  select_stmt->order_ = order;
  select_stmt->aggr_fields_.swap(aggr_fields);
  select_stmt->having_fields_.swap(having_fields);
  select_stmt->having_ops_.swap(having_ops);
  select_stmt->isfunc_ = select_sql.is_func;
  select_stmt->isvaluefunc_ = select_sql.is_value_func;

  // order
  if (order) select_stmt->order_fields_.swap(order_fields);

  // group
  select_stmt->group_ = group;
  if (group) select_stmt->group_fields_.swap(group_fields);
  select_stmt->having_ = having;
  select_stmt->having_filters_ = having_filters;

  select_stmt->attr_num_ = select_sql.attr_num;

  for (int a = 0; a < select_sql.attr_num; ++a) {
    select_stmt->func_value_list1_[a] = select_sql.func_value_list1[a];
    select_stmt->func_value_list2_[a] = select_sql.func_value_list2[a];
    select_stmt->func_op_list_[a] = select_sql.func_op_list[a];

    if (select_stmt->func_op_list_[a] != NO_FUNCTION_OP && select_stmt->func_value_list1_[a].type != UNDEFINED) {
      Value& v = select_stmt->func_value_list1_[a];
      if (select_stmt->func_op_list_[a] == LENGTH_OP) {

        if (v.type != CHARS) return RC::MISS_TYPE;

        *(int*)v.data = strlen((char*)v.data);
        v.type = INTS;
      } else if (select_stmt->func_op_list_[a] == ROUND_OP) {

        if (v.type != FLOATS && v.type != UNDEFINED) return RC::MISS_TYPE;

        if (select_stmt->func_value_list2_[a].type == INTS) {
          *(float*)v.data = round(*(float*)v.data, *(int*)(select_stmt->func_value_list2_[a].data));
        } else {
          *(float*)v.data = round1(*(float*)v.data);
        }
        v.type = FLOATS;
      } else if (select_stmt->func_op_list_[a] == DATE_FORMAT_OP) {

        if (v.type != DATES) return RC::MISS_TYPE;
        
        v.data = date_format(*(int*)v.data, (char*)select_stmt->func_value_list2_[a].data);
        v.type = CHARS;
      } else return RC::MISS_TYPE;
    }
  }
  
  stmt = select_stmt;
  return RC::SUCCESS;
}
