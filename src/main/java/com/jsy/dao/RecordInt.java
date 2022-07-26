package com.jsy.dao;

import com.jsy.bean.Record;

import java.util.List;

public interface RecordInt {
    public List<Record> getByBookId(long bookId);
}
