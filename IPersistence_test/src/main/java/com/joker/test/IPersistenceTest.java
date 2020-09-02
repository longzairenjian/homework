package com.joker.test;

import com.joker.io.Resources;
import com.joker.pojo.User;
import com.joker.dao.IUserDao;
import com.joker.sqlSession.SqlSession;
import com.joker.sqlSession.SqlSessionFactory;
import com.joker.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(1);
        user.setUsername("joker");

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        List<User> all = userDao.findAll();
        for (User user1 : all) {
            System.out.println(user1);
        }
    }

    /**
     * 添加
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(2);
        user.setUsername("rose");

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        userDao.saveUser(user);

    }

    /**
     * 删除
     * @throws Exception
     */
    @Test
    public void test3() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(2);
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        userDao.deleteUser(user);

    }
    @Test
    public void test4() throws Exception {
        InputStream resourceAsSteam = Resources.getResourceAsSteam("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsSteam);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //调用
        User user = new User();
        user.setId(4);
        user.setUsername("bill");

        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        userDao.updateUser(user);

    }
}
