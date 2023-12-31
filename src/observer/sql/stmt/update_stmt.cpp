/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */


#include "sql/stmt/update_stmt.h"
#include "common/log/log.h"
#include "sql/parser/parse_defs.h"
#include "storage/common/db.h"

/*************************************************upselect*********************************************************/
#include "sql/operator/project_operator.h"
#include "sql/operator/predicate_operator.h"
#include "sql/executor/execute_stage.h"
#include "sql/operator/table_scan_operator.h"
/************************************************upselect*********************************************************/


UpdateStmt::UpdateStmt(Table *table, 
                        Value *values,
                        int value_amount, 
                        FilterStmt *filter_stmt, 
                        char **attribute_name, 
                        size_t values_num)
            : table_ (table), 
              values_(values),
              value_amount_(value_amount), 
              filter_stmt_(filter_stmt), 
              attribute_name_(attribute_name), 
              values_num_(values_num)
{}
 /*************************************************upselect*********************************************************/

/*******************************************This file need to be modified******************************************************/
RC UpdateStmt::create(Db *db, const Updates &update, Stmt *&stmt)
{
  const char *table_name = update.relation_name;
  if (nullptr == db || nullptr == table_name) {
    LOG_WARN("invalid argument. db=%p, table_name=%p", 
             db, table_name);
    return RC::INVALID_ARGUMENT;
  }

  // check whether the table exists
  Table *table = db->find_table(table_name);
  if (nullptr == table) {
    LOG_WARN("no such table. db=%s, table_name=%s", db->name(), table_name);
    return RC::SCHEMA_TABLE_NOT_EXIST;
  }

  std::unordered_map<std::string, Table *> table_map;
  table_map.insert(std::pair<std::string, Table *>(std::string(table_name), table));

  FilterStmt *filter_stmt = nullptr;
  RC rc = FilterStmt::create(db, table, &table_map,
			     update.conditions, update.condition_num, filter_stmt);
  if (rc != RC::SUCCESS) {
    LOG_WARN("failed to create filter statement. rc=%d:%s", rc, strrc(rc));
    return rc;
  }


  stmt = new UpdateStmt(table, update.values, 1, filter_stmt, const_cast<char **>(update.attribute_names), update.values_num);
  UpdateStmt* u_stmt = dynamic_cast<UpdateStmt*>(stmt);
  Stmt* sstmt;

  for (int i = 0; i < update.values_num; i++) {
    if (update.select_vec[i] == nullptr) {
      // non select clause found
      continue;
    }
    // 0. check if table names are same to update's
    for (int a = 0; a < update.select_vec[i]->relation_num; a++) {
      if (0 == strcmp(update.select_vec[i]->relations[a], table_name)) {
        printf("You can not specify target table '%s' for update in FROM clause\n", table_name);
        return RC::SUB_SAME_TABLE;
      }
    }
    // 1. create select_stmt
    rc = SelectStmt::create(db, *(update.select_vec[i]), sstmt, nullptr);
    if (rc != RC::SUCCESS) {
      LOG_WARN("update-select: fail to create sub query");
      // printf("update-select: create sub query failed\n");
      return RC::GENERIC_ERROR;
    }
    auto s_stmt = dynamic_cast<SelectStmt*>(sstmt);
    // 2. create project_oper
    PredicateOperator pred_oper(s_stmt->filter_stmt());
    std::vector<Operator *> scan_opers(s_stmt->tables().size());
    for (int j = 0; j< scan_opers.size(); ++j) {
      printf("add scan operator %d\n", j);
      scan_opers[j] = ExecuteStage::try_to_create_index_scan_operator(s_stmt->filter_stmt());

      if (nullptr == scan_opers[j]) scan_opers[j] = new TableScanOperator(s_stmt->tables()[j]);

      pred_oper.add_child(scan_opers[j]);
    }
    ProjectOperator project_oper;
    project_oper.add_child(&pred_oper);
    bool is_single_table = false;
    if (s_stmt->tables().size() == 1) is_single_table = true;

    for (const Field &field : s_stmt->query_fields()) {
      if (field.meta() != nullptr) {
        project_oper.add_projection(field.table(), field.meta(), is_single_table);
      }
    }
    rc = project_oper.open();
    if (rc != RC::SUCCESS) {
      LOG_WARN("failed to open operator");
      return rc;
    }

    // 3. do select
    bool is_not_aggr = s_stmt->aggregation_ops().empty();
    if (!is_not_aggr) {
      // aggregation
      std::vector<Value> values(s_stmt->aggregation_ops().size());
      // printf("select aggregation: op size %zu\n", values.size());
      rc = ExecuteStage::aggregation_select_handler(s_stmt, values, project_oper);
      if (rc != RC::SUCCESS) return rc;

      if (values.size() != 1) {
        printf("sub-query result more than 1 rows\n");
        return RC::SUB_ROW_ERROR;
      }

      *const_cast<Value*>(&u_stmt->values_[i]) = values[0];

    } else {
      // normal select
      Tuple *tuple;
      TupleCell cell;
      int j = 0;
      while ((rc = ExecuteStage::normal_select_handler(s_stmt, tuple, project_oper)) == RC::SUCCESS) {
        tuple->cell_at(0, cell);
        if (tuple->cell_num() != 1) {
          printf("Operand should contain at least 1 column\n");
          return RC::SUB_COL_ERROR;
        }
        j += 1;
        if (j > 1) {
          printf("sub-query result more than 1 rows\n");
          return RC::SUB_ROW_ERROR;
        }
      }
      
      if (j == 0) {
        printf("sub-query result less than 1 row\n");
        return RC::SUB_ROW_ERROR;
      }
      
      const_cast<Value*>(&u_stmt->values_[i])->type = cell.attr_type();

      if (cell.attr_type() != NULLS) {
        size_t len;
        switch(cell.attr_type()) {
          case INTS:{
            len = sizeof(int);
          } break;
          case CHARS: {
            len = strlen((char*)cell.data());
          } break;
          case FLOATS: {
            len = sizeof(float);
          } break;
          default: {
            LOG_WARN("bad type in update stmt");
            return RC::SUB_BAD_TYPE;
          } break;
        }
        
        if (cell.attr_type() == CHARS) {
          const_cast<Value*>(&u_stmt->values_[i])->data = strdup(cell.data());
        } else {
          const_cast<Value*>(&u_stmt->values_[i])->data = malloc(len);
          memcpy(const_cast<Value*>(&u_stmt->values_[i])->data, (void*)cell.data(), len);
        }

      }
      
    }
  }

  return RC::SUCCESS;
}






