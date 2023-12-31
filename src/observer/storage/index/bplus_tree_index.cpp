/* Copyright (c) 2021 Xie Meiyi(xiemeiyi@hust.edu.cn) and OceanBase and/or its affiliates. All rights reserved.
miniob is licensed under Mulan PSL v2.
You can use this software according to the terms and conditions of the Mulan PSL v2.
You may obtain a copy of Mulan PSL v2 at:
         http://license.coscl.org.cn/MulanPSL2
THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
See the Mulan PSL v2 for more details. */

#include "storage/index/bplus_tree_index.h"
#include "common/log/log.h"
#include "sql/parser/parse_defs.h"

BplusTreeIndex::~BplusTreeIndex() noexcept
{
  close();
}

RC BplusTreeIndex::create(const char *file_name, const IndexMeta &index_meta, const FieldMeta &field_meta)
{
  return create(file_name, index_meta, std::vector<const FieldMeta*>{&field_meta});
}

RC BplusTreeIndex::create(const char *file_name, const IndexMeta &index_meta, const std::vector<const FieldMeta*> &field_meta) {
  if (inited_) {
    LOG_WARN("Failed to create index due to the index has been created before. file_name:%s, index:%s, first field:%s, field size:%d",
        file_name,
        index_meta.name(),
        index_meta.field()[0],
        index_meta.field().size());
    return RC::RECORD_OPENNED;
  }

  Index::init(index_meta, field_meta);

  int field_meta_cnt = field_meta.size();
  std::vector<AttrType> attr_type(field_meta_cnt);
  std::vector<int> attr_len(field_meta_cnt);
  for (int i = 0; i < field_meta_cnt; ++i) {
    attr_type[i] = field_meta[i]->type();
    attr_len[i] = field_meta[i]->len();
  } 
  RC rc = index_handler_.create(file_name, attr_type, attr_len);
  if (RC::SUCCESS != rc) {
    LOG_WARN("Failed to create index_handler, file_name:%s, index:%s, first field:%s, field size:%d, rc:%s",
        file_name,
        index_meta.name(),
        index_meta.field()[0],
        index_meta.field().size(),
        strrc(rc));
    return rc;
  }

  inited_ = true;
  LOG_INFO(
      "Successfully create index, file_name:%s, index:%s, first field:%s, field size:%d", file_name, index_meta.name(), index_meta.field()[0], index_meta.field().size());
  return rc;

}

RC BplusTreeIndex::open(const char *file_name, const IndexMeta &index_meta, const FieldMeta &field_meta)
{
  return open(file_name, index_meta, std::vector<const FieldMeta *>{&field_meta});
}

RC BplusTreeIndex::open(const char *file_name, const IndexMeta &index_meta, const std::vector<const FieldMeta*> &field_meta) {
  if (inited_) {
    LOG_WARN("Failed to open index due to the index has been initedd before. file_name:%s, index:%s, first field:%s, field size:%d",
        file_name,
        index_meta.name(),
        index_meta.field()[0],
        index_meta.field().size());
    return RC::RECORD_OPENNED;
  }

  Index::init(index_meta, field_meta);

  RC rc = index_handler_.open(file_name);
  if (RC::SUCCESS != rc) {
    LOG_WARN("Failed to open index_handler, file_name:%s, index:%s, first field:%s, field size:%d, rc:%s",
        file_name,
        index_meta.name(),
        index_meta.field()[0],
        index_meta.field().size(),
        strrc(rc));
    return rc;
  }

  inited_ = true;
  LOG_INFO(
      "Successfully open index, file_name:%s, index:%s, first field:%s, field size:%d", file_name, index_meta.name(), index_meta.field()[0],
        index_meta.field().size());
  return rc;
   
}

RC BplusTreeIndex::close()
{
  if (inited_) {
    LOG_INFO("Begin to close index, index:%s, first field:%s, field size:%d",
        index_meta_.name(), index_meta_.field()[0], index_meta_.field().size());
    index_handler_.close();
    inited_ = false;
  }
  LOG_INFO("Successfully close index.");
  return RC::SUCCESS;
}

RC BplusTreeIndex::insert_entry(const char *record, const RID *rid)
{
  std::vector<const char *> key; 
  for (int i = 0; i < field_meta_.size(); ++i) {
    key.push_back(record + field_meta_[i].offset());
  }  
  return index_handler_.insert_entry(key, rid);
  // return index_handler_.insert_entry(record + field_meta_.offset(), rid);
}

RC BplusTreeIndex::delete_entry(const char *record, const RID *rid)
{
  std::vector<const char *> key; 
  for (int i = 0; i < field_meta_.size(); ++i) {
    key.push_back(record + field_meta_[i].offset());
  }  
  return index_handler_.delete_entry(key, rid);
  // return index_handler_.delete_entry(record + field_meta_.offset(), rid);
}

IndexScanner *BplusTreeIndex::create_scanner(const std::vector<const char *> &left_key, const std::vector<int> &left_len, bool left_inclusive,
          const std::vector<const char *> &right_key, const std::vector<int> &right_len, bool right_inclusive)
// IndexScanner *BplusTreeIndex::create_scanner(const char *left_key, int left_len, bool left_inclusive,
// 					     const char *right_key, int right_len, bool right_inclusive)
{
  BplusTreeIndexScanner *index_scanner = new BplusTreeIndexScanner(index_handler_);
  RC rc = index_scanner->open(left_key, left_len, left_inclusive, right_key, right_len, right_inclusive);
  if (rc != RC::SUCCESS) {
    LOG_WARN("failed to open index scanner. rc=%d:%s", rc, strrc(rc));
    delete index_scanner;
    return nullptr;
  }
  return index_scanner;
}

RC BplusTreeIndex::sync()
{
  return index_handler_.sync();
}

////////////////////////////////////////////////////////////////////////////////
BplusTreeIndexScanner::BplusTreeIndexScanner(BplusTreeHandler &tree_handler) : tree_scanner_(tree_handler)
{}

BplusTreeIndexScanner::~BplusTreeIndexScanner() noexcept
{
  tree_scanner_.close();
}

RC BplusTreeIndexScanner::open(const std::vector<const char *> &left_key, const std::vector<int> &left_len, bool left_inclusive,
          const std::vector<const char *> &right_key, const std::vector<int> &right_len, bool right_inclusive)
// RC BplusTreeIndexScanner::open(const char *left_key, int left_len, bool left_inclusive,
//                                const char *right_key, int right_len, bool right_inclusive)
{
  return tree_scanner_.open(left_key, left_len, left_inclusive, right_key, right_len, right_inclusive);
}

RC BplusTreeIndexScanner::next_entry(RID *rid)
{
  return tree_scanner_.next_entry(rid);
}

RC BplusTreeIndexScanner::destroy()
{
  delete this;
  return RC::SUCCESS;
}
