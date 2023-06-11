/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */

#include <common/lang/string.h>
#include "storage/common/field_meta.h"
#include "common/log/log.h"

#include "json/json.h"

const static Json::StaticString FIELD_NAME("name");
const static Json::StaticString FIELD_TYPE("type");
const static Json::StaticString FIELD_OFFSET("offset");
const static Json::StaticString FIELD_LEN("len");
const static Json::StaticString FIELD_VISIBLE("visible");
const static Json::StaticString FILED_NULLABLE("nullable");

const char *ATTR_TYPE_NAME[] = {"undefined", "chars", "ints", "floats", "dates"};

const char *attr_type_to_string(AttrType type)
{
  if (type >= UNDEFINED && type <= DATES) {
    return ATTR_TYPE_NAME[type];
  }
  return "unknown";
}

AttrType attr_type_from_string(const char *s)
{
  for (unsigned int i = 0; i < sizeof(ATTR_TYPE_NAME) / sizeof(ATTR_TYPE_NAME[0]); i++) {
    if (0 == strcmp(ATTR_TYPE_NAME[i], s)) {
      return (AttrType)i;
    }
  }
  return UNDEFINED;
}

FieldMeta::FieldMeta() : attr_type_(AttrType::UNDEFINED), attr_offset_(-1), attr_len_(0), visible_(false), nullable_(false)
{}

RC FieldMeta::init(const char *name, AttrType attr_type, int attr_offset, int attr_len, bool visible, int is_nullable)
{
  // initialize attributes
  name_ = name;
  attr_type_ = attr_type;
  attr_len_ = attr_len;
  attr_offset_ = attr_offset;
  visible_ = visible;
  nullable_ = is_nullable == 1 ? true : false;
  
  LOG_INFO("Init a field with name=%s", name);
  return RC::SUCCESS;
}

const char *FieldMeta::name() const
{
  return name_.c_str();
}

AttrType FieldMeta::type() const
{
  return attr_type_;
}

int FieldMeta::offset() const
{
  return attr_offset_;
}

int FieldMeta::len() const
{
  return attr_len_;
}

bool FieldMeta::visible() const
{
  return visible_;
}

bool FieldMeta::nullable() const {
  return nullable_;
}

void FieldMeta::desc(std::ostream &os) const
{
  os << "field name=" << name_ << ", type=" << attr_type_to_string(attr_type_) << ", len=" << attr_len_
     << ", visible=" << (visible_ ? "yes" : "no") << ", nullable=" << (nullable_ ? "yes" : "no");
}

void FieldMeta::to_json(Json::Value &json_value) const
{
  json_value[FIELD_NAME] = name_;
  json_value[FIELD_TYPE] = attr_type_to_string(attr_type_);
  json_value[FIELD_OFFSET] = attr_offset_;
  json_value[FIELD_LEN] = attr_len_;
  json_value[FIELD_VISIBLE] = visible_;
  json_value[FILED_NULLABLE] = nullable_;
}

RC FieldMeta::from_json(const Json::Value &json_value, FieldMeta &field)
{
  if (!json_value.isObject()) {
    LOG_ERROR("Failed to deserialize field. json is not an object. json value=%s", json_value.toStyledString().c_str());
    return RC::GENERIC_ERROR;
  }

  const Json::Value &name_value = json_value[FIELD_NAME];
  const Json::Value &type_value = json_value[FIELD_TYPE];
  const Json::Value &offset_value = json_value[FIELD_OFFSET];
  const Json::Value &len_value = json_value[FIELD_LEN];
  const Json::Value &visible_value = json_value[FIELD_VISIBLE];
  const Json::Value &nullable_value = json_value[FILED_NULLABLE];

  if (!name_value.isString()) {
    LOG_ERROR("Field name is not a string. json value=%s", name_value.toStyledString().c_str());
    return RC::GENERIC_ERROR;
  }
  if (!type_value.isString()) {
    LOG_ERROR("Field type is not a string. json value=%s", type_value.toStyledString().c_str());
    return RC::GENERIC_ERROR;
  }

  if (!offset_value.isInt()) {
    LOG_ERROR("Offset is not an integer. json value=%s", offset_value.toStyledString().c_str());
    return RC::GENERIC_ERROR;
  }
  if (!len_value.isInt()) {
    LOG_ERROR("Len is not an integer. json value=%s", len_value.toStyledString().c_str());
    return RC::GENERIC_ERROR;
  }
  if (!visible_value.isBool()) {
    LOG_ERROR("Visible field is not a bool value. json value=%s", visible_value.toStyledString().c_str());
    return RC::GENERIC_ERROR;
  }
  if (!nullable_value.isBool()) {
    LOG_ERROR("Nullable field is not a bool value. json value=%s", nullable_value.toStyledString().c_str());
    return RC::GENERIC_ERROR;
  }

  AttrType type = attr_type_from_string(type_value.asCString());
  if (UNDEFINED == type) {
    LOG_ERROR("Got invalid field type. type=%d", type);
    return RC::GENERIC_ERROR;
  }

  const char *name = name_value.asCString();
  int offset = offset_value.asInt();
  int len = len_value.asInt();
  bool visible = visible_value.asBool();
  bool nullable = nullable_value.asBool();
  int is_nullable = nullable ? 1 : 0;
  return field.init(name, type, offset, len, visible, is_nullable);
}