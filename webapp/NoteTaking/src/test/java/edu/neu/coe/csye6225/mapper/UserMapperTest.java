package edu.neu.coe.csye6225.mapper;

import static org.junit.Assert.*;
import edu.neu.coe.csye6225.entity.User;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.UUID;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    @Test
    public void testGetAllUsers() {
        User user1 = new User(UUID.randomUUID().toString(),"bbbb");
        User user2 = new User(UUID.randomUUID().toString(),"cccc");
        int size1 = userMapper.getAllUsers().size();
        userMapper.insertUser(user1);
        userMapper.insertUser(user2);
        assertEquals(size1+2,userMapper.getAllUsers().size());
    }

    @Test
    public void testGetUserByUsername() {
        User user = new User(UUID.randomUUID().toString(),"bbb");
        userMapper.insertUser(user);
        assertNotNull(userMapper.getUserByUsername(user.getUsername()));
    }

    @Test
    public void testUpdateUser(){
        User user = new User(UUID.randomUUID().toString(),"bbb");
        userMapper.insertUser(user);
        user.setPassword("ccc");
        userMapper.updateUser(user);
        assertNotEquals("bbb",userMapper.getUserByUsername(user.getUsername()).getPassword());
    }


    @Test
    public void testInsertUser(){
        User user = new User(UUID.randomUUID().toString(),"bbb");
        assertNull(userMapper.getUserByUsername(user.getUsername()));
        userMapper.insertUser(user);
        assertNotNull(userMapper.getUserByUsername(user.getUsername()));
    }

    @Test
    public void testDeleteUserByUsername(){
        User user = new User(UUID.randomUUID().toString(),"bbb");
        userMapper.insertUser(user);
        assertNotNull(userMapper.getUserByUsername(user.getUsername()));
        userMapper.deleteUserByUsername(user.getUsername());
        assertNull(userMapper.getUserByUsername(user.getUsername()));
    }

}
