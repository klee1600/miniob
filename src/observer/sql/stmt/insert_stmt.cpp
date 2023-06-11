/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */

#include "sql/stmt/insert_stmt.h"
#include "common/log/log.h"
#include "storage/common/db.h"
#include "storage/common/table.h"

#include "util/util.h"

InsertStmt::InsertStmt(Table *table, std::vector<const Value *> &tuples, const size_t *value_num, size_t tuple_num)
    : table_(table), tuples_(tuples), tuple_num_(tuple_num), value_num_(value_num)
{
}

RC InsertStmt::create(Db *db, const Inserts &inserts, Stmt *&stmt)
{
  const char *table_name = inserts.relation_name;
  if (nullptr == db || nullptr == table_name || inserts.tuple_num <= 0)
  {
    LOG_WARN("invalid argument. db=%p, table_name=%p, value_num=%d",
             db, table_name, inserts.value_num);
    return RC::INVALID_ARGUMENT;
  }

  // check whether the table exists
  Table *table = db->find_table(table_name);
  if (nullptr == table)
  {
    LOG_WARN("no such table. db=%s, table_name=%s", db->name(), table_name);
    return RC::SCHEMA_TABLE_NOT_EXIST;
  }
  int insert_tuple_num = inserts.tuple_num;
  std::vector<const Value *> tuples(insert_tuple_num);
  for (int i = 0; i < insert_tuple_num; ++i)
  {
    tuples[i] = inserts.tuples[i];
  }

  // const Value *tuples[MAX_NUM] = inserts.tuples;
  const size_t tuple_num = inserts.tuple_num;
  printf("tuple number is %zu\n", tuple_num);
  const TableMeta &table_meta = table->table_meta();
  const size_t *value_num = inserts.value_num;
  const int field_num = table_meta.field_num() - table_meta.sys_field_num();
  for (int i = 0; i < inserts.tuple_num; ++i)
  {
    if (field_num != value_num[i])
    {
      LOG_WARN("schema mismatch. value num=%d, field num in schema=%d", value_num, field_num);
      return RC::SCHEMA_FIELD_MISSING;
    }
  }

  // check fields type
  const int sys_field_num = table_meta.sys_field_num();
  for (int k = 0; k < tuple_num; ++k)
  {

/*
compile failed,/home/miniob/miniob_test/players/yujinjia/miniob/src/observer/sql/stmt/insert_stmt.cpp:
 In static member function 'static RC InsertStmt::create(Db*, const Inserts&, Stmt*&)':,
 /home/miniob/miniob_test/players/yujinjia/miniob/src/observer/sql/stmt/insert_stmt.cpp:70:35: 
 error: 'field_meta' was not declared in this scope; 
 did you mean 'FieldMeta'?,   70 |       const AttrType field_type = field_meta->type();,      
 |                                   ^~~~~~~~~~,      
 |                                   FieldMeta,make[2]: *** [src/observer/CMakeFiles/observer.dir/sql/stmt/insert_stmt.cpp.o] 
 Error 1,make[2]: *** Waiting for unfinished jobs....,make[1]:
  *** [src/observer/CMakeFiles/observer.dir/all] Error 2,make[1]: 
  *** Waiting for unfinished jobs....,
  /home/miniob/miniob_test/players/yujinjia/miniob/src/observer/sql/stmt/insert_stmt.cpp: 
  In static member function 'static RC InsertStmt::create(Db*, const Inserts&, Stmt*&)':
  ,/home/miniob/miniob_test/players/yujinjia/miniob/src/observer/sql/stmt/insert_stmt.cpp:70:35: error:
   'field_meta' was not declared in this scope; 
   did you mean 'FieldMeta'?,   70 |       const AttrType field_type = field_meta->type();,      
   |                                   ^~~~~~~~~~,      |                                   
   FieldMeta,make[2]: *** [src/observer/CMakeFiles/observer_static.dir/sql/stmt/insert_stmt.cpp.o]
    Error 1,make[2]: *** Waiting for unfinished jobs....,make[1]: *** [src/observer/CMakeFiles/observer_static.dir/all] 
    Error 2,make: *** [all] Error 2,,failed to run make,compile failed*/
    for (int i = 0; i < value_num[k]; i++)
    {
      const AttrType value_type = tuples[k][i].type;
      const FieldMeta *field_meta = table_meta.field(i + sys_field_num);
      const AttrType field_type = field_meta->type();
      if (field_type != value_type)
      {
        if (value_type == NULLS)
        {
          // field 为 nullable，合法 否则不合法
          if (field_meta->nullable())
          {
            continue;
          }
          else
          {
            LOG_WARN("insert null into non-nullable field!\n");
            return RC::NULL_OP_ON_NON_NULLABLE;
          }
        }

        // type cast
        if (field_type == INTS)
        {
          if (value_type == FLOATS)
          {
            AttrType *att = const_cast<AttrType *>(&tuples[k][i].type);
            *att = INTS;
            float *data = static_cast<float *>(tuples[k][i].data);
            int value = (int)*data;
            if (*data - (float)value >= 0.5)
              value += 1;
            int *int_data = static_cast<int *>(tuples[k][i].data);
            *int_data = value;
          }
          else if (value_type == CHARS)
          {
            float num = 0;
            std::string str = static_cast<char *>(tuples[k][i].data);
            string2float(str, &num);
            AttrType *att = const_cast<AttrType *>(&tuples[k][i].type);
            *att = INTS;
            int *data = static_cast<int *>(tuples[k][i].data);

            if (num - (float)((int)num) >= 0.5)
              num += 1;
            *data = (int)num;
          }
          else
          {
            LOG_WARN("field type mismatch. table=%s, field=%s, field type=%d, value_type=%d",
                     table_name, field_meta->name(), field_type, value_type);
            return RC::SCHEMA_FIELD_TYPE_MISMATCH;
          }
        }
        else if (field_type == FLOATS)
        {
          if (value_type == INTS)
          {
            AttrType *att = const_cast<AttrType *>(&tuples[k][i].type);
            *att = FLOATS;
            int *data = static_cast<int *>(tuples[k][i].data);
            float value = (float)*data;
            float *float_data = static_cast<float *>(tuples[k][i].data);
            *float_data = value;
          }
          else if (value_type == CHARS)
          {

            float num = 0;
            std::string str = static_cast<char *>(tuples[k][i].data);
            string2float(str, &num);
            AttrType *att = const_cast<AttrType *>(&tuples[k][i].type);
            *att = FLOATS;
            float *data = static_cast<float *>(tuples[k][i].data);
            *data = num;
          }
          else
          {
            LOG_WARN("field type mismatch. table=%s, field=%s, field type=%d, value_type=%d",
                     table_name, field_meta->name(), field_type, value_type);
            return RC::SCHEMA_FIELD_TYPE_MISMATCH;
          }
        }
        else if (field_type == CHARS)
        {
          if (value_type == INTS)
          {

            char *str = static_cast<char *>(tuples[k][i].data);
            int *data = static_cast<int *>(tuples[k][i].data);
            char *s = const_cast<char *>(std::to_string(*data).data());
            for (int i = 0; s[i] != '\0'; i++)
            {
              str[i] = s[i];
            }
            AttrType *att = const_cast<AttrType *>(&tuples[k][i].type);
            *att = CHARS;
          }
          else if (value_type == FLOATS)
          {
            char *str = static_cast<char *>(tuples[k][i].data);
            float *data = static_cast<float *>(tuples[k][i].data);
            char *s = const_cast<char *>(double2string(*data).data());
            int idx = 0;
            for (; s[idx] != '\0'; idx++)
            {
              str[idx] = s[idx];
            }
            str[idx] = '\0';
            AttrType *att = const_cast<AttrType *>(&tuples[k][i].type);
            *att = CHARS;
          }
          else
          {
            LOG_WARN("field type mismatch. table=%s, field=%s, field type=%d, value_type=%d",
                     table_name, field_meta->name(), field_type, value_type);
            return RC::SCHEMA_FIELD_TYPE_MISMATCH;
          }
        }
        else
        {
          LOG_WARN("field type mismatch. table=%s, field=%s, field type=%d, value_type=%d",
                   table_name, field_meta->name(), field_type, value_type);
          return RC::SCHEMA_FIELD_TYPE_MISMATCH;
        }
      }
    }
  }

  // everything is alright!
  stmt = new InsertStmt(table, tuples, value_num, tuple_num);
  return RC::SUCCESS;
}
