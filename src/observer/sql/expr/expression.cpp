/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */

#include "sql/expr/expression.h"
#include "common/log/log.h"
#include "sql/expr/tuple_cell.h"
#include "sql/operator/predicate_operator.h"
#include "sql/operator/project_operator.h"
#include "sql/operator/table_scan_operator.h"
// #include "sql/expr/tuple.h"
#include "sql/executor/execute_stage.h"
#include "sql/parser/parse_defs.h"


RC FieldExpr::get_value(const Tuple &tuple, TupleCell &cell) const
{
  return tuple.find_cell(field_, cell);
}

RC ValueExpr::get_value(const Tuple &tuple, TupleCell & cell) const
{
  cell = tuple_cell_;
  return RC::SUCCESS;
}

RC SubQueryExpr::get_value(const Tuple &tuple, TupleCell & cell) const {

  RC rc = RC::SUCCESS;

  PredicateOperator pred_oper(select_stmt_->filter_stmt());

  std::vector<Operator *> scan_opers(select_stmt_->tables().size());
  for (int i = 0; i< scan_opers.size(); ++i) {
    printf("add scan operator %d\n", i);
    scan_opers[i] = ExecuteStage::try_to_create_index_scan_operator(select_stmt_->filter_stmt());
    if (nullptr == scan_opers[i]) {
      scan_opers[i] = new TableScanOperator(select_stmt_->tables()[i]);
    }
    pred_oper.add_child(scan_opers[i]);
  }

  pred_oper.set_parent_tuple(const_cast<Tuple*>(&tuple));

  ProjectOperator project_oper;
  project_oper.add_child(&pred_oper);

  bool is_single_table = false; 
  if (select_stmt_->tables().size() == 1) is_single_table = true;

  for (const Field &field : select_stmt_->query_fields()) {
    if (field.meta() != nullptr) {
      project_oper.add_projection(field.table(), field.meta(), is_single_table);
    }
  }
  rc = project_oper.open();

  Tuple *tmp_tuple;

  if (select_stmt_->aggregation_ops().size() > 0) {
    if (select_stmt_->aggregation_ops().size() > 1) {
      LOG_WARN("more than one result of the sub query aggregation");
      //printf("more than one result of the sub query aggregation\n");
      return RC::GENERIC_ERROR;
    }

    std::vector<Value> values(1);
    // aggregation select
    rc = ExecuteStage::aggregation_select_handler(select_stmt_, values, project_oper);
    if (rc != RC::SUCCESS) return rc;

    cell.set_type(values[0].type);
    cell.set_data((char *)values[0].data);
    // TODO: not sure
    switch (values[0].type) {
      case FLOATS:{
        cell.set_length(sizeof(int));
      } break;
      case INTS: {
        cell.set_length(sizeof(int));
      } break;
      case CHARS: {
        cell.set_length(strlen((char *)values[0].data));
      } break;
      default: break;
    }
    
  } else {

    // normal select
    int cnt = 0;
    while ((rc = ExecuteStage::normal_select_handler(select_stmt_, tmp_tuple, project_oper)) == RC::SUCCESS) {
      if (cnt == 1 || tmp_tuple->cell_num() != 1) {
        // when invoking get_value of a sub query expression the result must be exactly only one row
        LOG_WARN("more than one result of the sub query");
        printf("more than one result of the sub query\n");
        return RC::GENERIC_ERROR;
      }
      cnt = 1;
      rc = tmp_tuple->cell_at(0, cell);
    }

    if (rc != RC::RECORD_EOF) return RC::GENERIC_ERROR;

    if (cnt == 0) {
      // no result of this sub query
      LOG_WARN("no result of this sub-query");
      printf("no result of this sub-query\n");
      return RC::GENERIC_ERROR;
    }
  }
  
  return RC::SUCCESS;

}



RC SubQueryExpr::check_contain_or_exist(Tuple *parent_tuple, bool check_contain, TupleCell *left_cell, bool &res) {

  printf("sub query: start querying\n");

  // std::vector<Tuple*> tuple_set;
  RC rc = RC::SUCCESS;
  PredicateOperator pred_oper(select_stmt_->filter_stmt());

  std::vector<Operator *> scan_opers(select_stmt_->tables().size());
  for (int i = 0; i< scan_opers.size(); ++i) {
    printf("add scan operator %d\n", i);
    scan_opers[i] = ExecuteStage::try_to_create_index_scan_operator(select_stmt_->filter_stmt());
    if (nullptr == scan_opers[i]) {
      scan_opers[i] = new TableScanOperator(select_stmt_->tables()[i]);
    }
    pred_oper.add_child(scan_opers[i]);
  }

  pred_oper.set_parent_tuple(parent_tuple);

  ProjectOperator project_oper;
  project_oper.add_child(&pred_oper);

  bool is_single_table = false;
  if (select_stmt_->tables().size() == 1) is_single_table = true;

  for (const Field &field : select_stmt_->query_fields()) {
    if (field.meta() != nullptr) {
      project_oper.add_projection(field.table(), field.meta(), is_single_table);
    }
  }
  rc = project_oper.open();

  Tuple *tuple;
  bool check_exist = !check_contain;
  while ((rc = ExecuteStage::normal_select_handler(select_stmt_, tuple, project_oper)) == RC::SUCCESS) {
    printf("sub_query: do_select: get a tuple\n");
    // tuple_set.push_back(tuple);
    TupleCell tmp_cell;
    rc = tuple->cell_at(0, tmp_cell);
    if (rc != RC::SUCCESS) {
      LOG_WARN("sub query tuple get cell wrong");
      return rc;
    }
    if (check_exist) {
      if(tmp_cell.attr_type() == AttrType::NULLS) {
        // loop until find a tuple that isn't NULL
        continue;
      }
      res = true;
      return RC::SUCCESS;
    }

    // check contain
    if (tuple->cell_num() != 1) {
       return RC::GENERIC_ERROR;
    }
    if(left_cell->attr_type() == AttrType::NULLS || tmp_cell.attr_type() == AttrType::NULLS) {
      // loop until find a tuple that isn't NULL
      continue;
    }
    if (left_cell->compare(tmp_cell) == 0) {
      res = true;
      return RC::SUCCESS;
    }
  }
  
  if (rc != RC::RECORD_EOF) {
    return RC::GENERIC_ERROR;
  }

  res = false;
  return RC::SUCCESS;
}

RC SubQueryExpr::check_not_contain_or_exist(Tuple *parent_tuple, bool check_contain, TupleCell *left_cell, bool &res) {

  printf("sub query: start querying\n");

  // std::vector<Tuple*> tuple_set;
  RC rc;
  PredicateOperator pred_oper(select_stmt_->filter_stmt());

  std::vector<Operator *> scan_opers(select_stmt_->tables().size());
  for (int i = 0; i< scan_opers.size(); ++i) {
    printf("add scan operator %d\n", i);
    scan_opers[i] = ExecuteStage::try_to_create_index_scan_operator(select_stmt_->filter_stmt());
    if (nullptr == scan_opers[i]) {
      scan_opers[i] = new TableScanOperator(select_stmt_->tables()[i]);
    }
    pred_oper.add_child(scan_opers[i]);
  }

  pred_oper.set_parent_tuple(parent_tuple);

  ProjectOperator project_oper;
  project_oper.add_child(&pred_oper);

  bool is_single_table = false;
  if (select_stmt_->tables().size() == 1) is_single_table = true;

  for (const Field &field : select_stmt_->query_fields()) {
    if (field.meta() != nullptr) {
      project_oper.add_projection(field.table(), field.meta(), is_single_table);
    }
  }
  rc = project_oper.open();

  Tuple *tuple;

  bool check_exist = !check_contain;
  
  while ((rc = ExecuteStage::normal_select_handler(select_stmt_, tuple, project_oper)) == RC::SUCCESS) {
    printf("sub_query:do_select: get a tuple\n");
    // tuple_set.push_back(tuple);
    TupleCell tmp_cell;
    rc = tuple->cell_at(0, tmp_cell);
    if (rc != RC::SUCCESS) {
      LOG_WARN("sub query tuple get cell wrong");
      return rc;
    }

    if (check_exist) {
      // check not exist
      res = false;
      return RC::SUCCESS;
    }
    if (tuple->cell_num() != 1) {
      // the sub query must select only one field
       return RC::GENERIC_ERROR;
    }
    // check not contain
    if(tmp_cell.attr_type() == AttrType::NULLS) {
      res = false;
      return RC::SUCCESS;
    }
    if (left_cell->compare(tmp_cell) == 0) {
      res = false;
      return RC::SUCCESS;
    }
  }

  if (rc != RC::RECORD_EOF) {
    return RC::GENERIC_ERROR;
  }

  res = true;
  return RC::SUCCESS;

}

bool ValueSetExpr::check_contain(TupleCell &other_tuple_cell) const {
  for (auto &value: value_set_) {
    if (value.type != AttrType::NULLS) {
      TupleCell tmp = TupleCell(value.type, (char *)value.data);
      if (tmp.attr_type() != AttrType::NULLS) {
        if (other_tuple_cell.compare(tmp) == 0) {
          // printf("value set expr: find an equal value\n");
          return true;
        }
      }
    }
  }
  return false;
}

bool ValueSetExpr::check_not_contain(TupleCell &other_tuple_cell) const {
  for (auto &value: value_set_) {
    if (value.type == AttrType::NULLS) {
      return false;
    }
    TupleCell tmp = TupleCell(value.type, (char *)value.data);
    if (other_tuple_cell.compare(tmp) == 0) {
      printf("value set expr: find an equal value\n");
      return false;
    }
  }
  return true;
}
