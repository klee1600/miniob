/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */

#pragma once

#include "sql/operator/operator.h"
#include "rc.h"

class Trx;
class DeleteStmt;

class DeleteOperator : public Operator
{
public:
  DeleteOperator(DeleteStmt *delete_stmt, Trx *trx)
    : delete_stmt_(delete_stmt), trx_(trx)
  {}

  virtual ~DeleteOperator() = default;

  RC open() override;

  //201250227
  RC delete_records(Operator *child, Table *table);

  RC next() override;
  RC close() override;

  Tuple * current_tuple() override {
    return nullptr;
  }
  //int tuple_cell_num() const override
  //RC tuple_cell_spec_at(int index, TupleCellSpec &spec) const override
private:
  DeleteStmt *delete_stmt_ = nullptr;
  Trx *trx_ = nullptr;
};
