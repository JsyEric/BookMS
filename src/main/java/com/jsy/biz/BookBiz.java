package com.jsy.biz;

import com.jsy.SpringConfig.springConfiguration;
import com.jsy.bean.Book;
import com.jsy.bean.Type;
import com.jsy.dao.BookDao;
import com.jsy.dao.RecordDao;
import com.jsy.dao.TypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class BookBiz {
    @Autowired
    BookDao bookDao;
    public List<Book> getByTypeId(long typeId) throws SQLException {
        return bookDao.getByTypeId(typeId);
    }
    public int add(long typeId, String name, float price, String desc, String pic, String publish, String author,long stock,String address){
        int count = 0;
        try {
            count = bookDao.add(typeId,name,price,desc,pic,publish,author,stock,address);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int modify(long id, long typeId, String name, float price, String desc, String pic, String publish, String author,long stock,String address) throws SQLException {
        int count = 0;
        try {
            count = bookDao.modify(id,typeId,name,price,desc,pic,publish,author,stock,address);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int remove(long id) throws Exception {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
        RecordDao recordDao = app.getBean(RecordDao.class);
        int countBookId = recordDao.getByBookId(id);
        if(countBookId>0){
            throw new RuntimeException();
        }else {
            int count = 0;
            try {
                count = bookDao.remove(id);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return count;
        }
    }

    public List<Book> getByPage(int pageIndex, int pageSize) throws SQLException {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
        TypeDao typeDao = app.getBean(TypeDao.class);
        List<Book> bookList = null;
        try {
            bookList = bookDao.getByPage(pageIndex,pageSize);

            for (Book book:bookList){
                long typeId = book.getTypeId();
                Type type = typeDao.getById(typeId);
                book.setType(type);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookList;
    }

    public Book getById(long id) throws SQLException {
        Book book = null;
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
        TypeDao typeDao = app.getBean(TypeDao.class);
        try {
            book = bookDao.getById(id);
            long typeId = book.getTypeId();
            Type type = typeDao.getById(typeId);
            book.setType(type);
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPageCount(int pageSize) throws SQLException {
        int pageCount = 0;
        try {
            int rowCount = bookDao.getCount();
            pageCount = (rowCount-1)/pageSize+1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pageCount;
    }

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(springConfiguration.class);
        BookBiz bookBiz = app.getBean(BookBiz.class);
        System.out.println(bookBiz.getByPage(2,3));
    }
}
