package com.jsy.dao;

import com.jsy.bean.Record;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
@Repository
public class RecordDao {
    InputStream inputStream = UserDao.class.getClassLoader().getResourceAsStream("Config.xml");
    SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
    SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(inputStream);

    public int getByBookId(long bookId) throws SQLException {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        RecordInt recordInt = sqlSession.getMapper(RecordInt.class);
        List<Record> recordList = recordInt.getByBookId(bookId);
        int count = recordList.size();
        sqlSession.commit();
        sqlSession.close();
        return count;
    }

    public static void main(String[] args) throws SQLException {
        RecordDao recordDao = new RecordDao();
        int count = recordDao.getByBookId(3);
        System.out.println(count);
    }
}
