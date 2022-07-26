package com.jsy.dao;

import com.jsy.bean.Book;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BookInt {
    public List<Book> getByTypeId(long typeId);

    public int add(long typeId, String name, float price, String desc, String pic, String publish, String author,long stock,String address);

    public int modify(@Param("id") long id,@Param("typeId") long typeId, @Param("name")String name, @Param("price")float price, @Param("desc")String desc, @Param("pic")String pic, @Param("publish")String publish, @Param("author")String author, @Param("stock")long stock, @Param("address")String address);

    public int remove(long id);

    public List<Book> getByPage(int pageIndex, int pageSize);

    public Book getById(long id);

    public int getCount();
}
