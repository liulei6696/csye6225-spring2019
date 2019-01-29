package edu.neu.coe.csye6225.mapper;

import edu.neu.coe.csye6225.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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
            for (int i = 0; i < users.size(); i++) {
                System.out.println(users.get(i));
            }
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
            System.out.println(user);
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
            user.setPassword("123345");
            System.out.println("---return info: 1 represents success, 0 represents failure.---");
            System.out.println(userMapper.updateUser(user));
            System.out.println("-----------");


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
            user.setUsername("23333");
            user.setPassword("66666");
            System.out.println("---return info: 1 represents success, 0 represents failure.---");
            System.out.println(userMapper.insertUser(user));
            System.out.println("-----------");

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

            System.out.println("------");
            System.out.println(userMapper.deleteUserByUsername("zzy"));
            User user = userMapper.getUserByUsername("zzy");
            if(user==null)
                System.out.println("success");
            else
                System.out.println("fail");

            System.out.println("-----------");

            if(user==null)
                System.out.println("success");
            else
                System.out.println("fail");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

}
