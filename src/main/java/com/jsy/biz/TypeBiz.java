package com.jsy.biz;

import com.jsy.bean.Book;
import com.jsy.bean.Type;
import com.jsy.dao.BookDao;
import com.jsy.dao.TypeDao;

import java.sql.SQLException;
import java.util.List;

public class TypeBiz {

    TypeDao typeDao = new TypeDao();
    public List<Type> getAll(){
        List<Type> typeList = null;
        try {
            typeList =  typeDao.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return typeList;
    }

    public  int add(String name, long parentId ){
        try {
            return typeDao.add(name,parentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  int modify(long id, String name, long parentId){
        try {
            return typeDao.modify(id,name,parentId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int remove(long id) throws Exception {
        try {
            BookDao bookDao = new BookDao();
            List<Book> bookList = bookDao.getByTypeId(id);
            if(bookList.size()>0){
                throw new Exception("删除的类型有子信息,删除失败");
            }
            return typeDao.remove(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Type getById(long id){
        try {
            return typeDao.getById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
