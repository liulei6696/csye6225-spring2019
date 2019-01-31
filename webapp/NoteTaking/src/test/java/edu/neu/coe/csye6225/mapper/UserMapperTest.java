package edu.neu.coe.csye6225.mapper;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.neu.coe.csye6225.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class UserMapperTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testGetAllUsers() {
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            List<User> users = sqlSession.selectList("getAllUsers");
            Assert.assertNotNull(users);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void testGetUserByUsername() {

        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            UserMapper userMapper = (UserMapper) sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUserByUsername("aaa");
            Assert.assertNotNull(user);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void testUpdateUser(){
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            UserMapper userMapper = (UserMapper) sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUserByUsername("aaa");
            Assert.assertNotEquals("1234@qqx",user.getPassword());
            user.setPassword("1234@qqx");
            userMapper.updateUser(user);
            Assert.assertEquals("1234@qqx",userMapper.getUserByUsername("aaa").getPassword());


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }


    @Test
    public void testInsertUser(){
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            UserMapper userMapper = (UserMapper) sqlSession.getMapper(UserMapper.class);
            User user = new User();
            user.setUsername("123@qq.com");
            user.setPassword("123!@#zxc");
            userMapper.insertUser(user);
            Assert.assertEquals(user.getPassword(),userMapper.getUserByUsername(user.getUsername()).getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void testDeleteUserByUsername(){
        SqlSession sqlSession = null;
        try {
            sqlSession = sqlSessionFactory.openSession();

            UserMapper userMapper = (UserMapper) sqlSession.getMapper(UserMapper.class);
            Assert.assertNotNull(userMapper.getUserByUsername("zzy"));
            userMapper.deleteUserByUsername("zzy");
            Assert.assertNull(userMapper.getUserByUsername("zzy"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

}
