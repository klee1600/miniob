/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */

#include "sql/expr/tuple_cell.h"
#include "storage/common/field.h"
#include "common/log/log.h"
#include "util/comparator.h"
#include "util/util.h"

void TupleCell::to_string(std::ostream &os) const
{
  switch (attr_type_) {
  case INTS: {
    os << *(int *)data_;
  } break;
  case FLOATS: {
    float v = *(float *)data_;
    os << double2string(v);
  } break;
  case CHARS: {
    for (int i = 0; i < length_; i++) {
      if (data_[i] == '\0') {
        break;
      }
      os << data_[i];
    }
  } break;
  case DATES: {
    int value = *(int*)data_;
    char buf[16] = {0};
    snprintf(buf, sizeof(buf), "%04d-%02d-%02d",value/10000, (value%10000)/100, value%100);
    os << buf;
  } break;
  case NULLS: {
    os << "NULL";
  } break;
  default: {
    LOG_WARN("unsupported attr type: %d", attr_type_);
  } break;
  }
}

int TupleCell::compare(const TupleCell &other) const
{
  if (this->attr_type_ == other.attr_type_) {
    switch (this->attr_type_) {
    case INTS: return compare_int(this->data_, other.data_);
    case DATES: return compare_int(this->data_, other.data_);
    case CHARS: return compare_string(this->data_, this->length_, other.data_, other.length_);
    case FLOATS: return compare_float(this->data_, other.data_);
    default: {
      LOG_WARN("unsupported type: %d", this->attr_type_);
    }
    }
  } else if (this->attr_type_ == INTS) {
    if (other.attr_type_ == FLOATS) { 
      float this_data = *(int *)data_;
      return compare_float(&this_data, other.data_);
    } else if (other.attr_type_ == CHARS) {
      float num = 0;
      string2float(other.data_, &num);
      int int_data = *((int*)this->data_);
      float f = (float)int_data;
      return compare_float(&f, &num);
    }
    // invalid compare
  } else if (this->attr_type_ == FLOATS) {
    if (other.attr_type_ == INTS) {
      float other_data = *(int *)other.data_;
      return compare_float(data_, &other_data);
    } else if (other.attr_type_ == CHARS) {
      float num = 0;
      string2float(other.data_, &num);
      return compare_float(this->data_, &num);
    }
    // invalid compare
  } else if(this->attr_type_ == CHARS) {
    if (other.attr_type_ == INTS) {
      float num = 0;
      string2float(this->data_, &num);
      int int_data = *((int*)other.data_);
      float f = (float)int_data;
      return compare_float(&num, &f);
    } else if (other.attr_type_ == FLOATS) {
      float num = 0;
      string2float(this->data_, &num);
      return compare_float(&num, other.data_);
    }
    // invalid compare
  }
  
  LOG_WARN("not supported");
  printf("not supported typecase: %d to %d\n", this->attr_type_, other.attr_type_);
  return -1;
}


/**********************************like*****************************************/
bool TupleCell::fuzzy_query_compare(const TupleCell &other) const 
{
  if (this->attr_type_ == CHARS && other.attr_type_ == CHARS) {
    std::string s1 = this->data_;
    std::string s2 = other.data_;
    return is_like(s1, s2);
  }
  printf("fuzzy query of non-chars type!");
  return false;
}
/**********************************like****************************************/
