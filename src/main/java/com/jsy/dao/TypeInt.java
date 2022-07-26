package com.jsy.dao;

import com.jsy.bean.Type;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface TypeInt {
    public int add(@Param("name") String name, @Param("parentId") long parentId);
    public List<Type> getAll();
    public Type getById(@Param("typeId") long typeId);
    public int modify(long id, String name, long parentId);
    public int remove(long id);
}
