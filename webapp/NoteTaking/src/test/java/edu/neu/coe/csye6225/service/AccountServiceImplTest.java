package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.UserMapper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/** 
* AccountServiceImpl Tester. 
* 
* @author <Authors name> 
* @since <pre>Jan 30, 2019</pre> 
* @version 1.0 
*/

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceImplTest {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserMapper userMapper;

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: logIn(User user) 
* 
*/ 
@Test
public void testLogIn() throws Exception {
    User user = new User();
    user.setUsername("xxs1990@qq.com");
    user.setPassword("123!@zxc");
    if(userMapper.getUserByUsername(user.getUsername())==null)
        accountService.signUp(user);
   //Assert.assertTrue(accountService.signUp(user));
    Assert.assertTrue(accountService.logIn(user));




} 

/** 
* 
* Method: signUp(User user) 
* 
*/ 
@Test
public void testSignUp() throws Exception { 
//TODO: Test goes here...
    User user = new User();
    user.setUsername("");
    user.setPassword("12345!@zxc");
    Assert.assertFalse(accountService.signUp(user));  //username or password can not be null
    user.setUsername("zzzzzz");
    Assert.assertFalse(accountService.signUp(user)); //username must be in email format
    user.setUsername("xxs1992@qq.com");
    if(userMapper.getUserByUsername(user.getUsername())!=null)
        userMapper.deleteUserByUsername(user.getUsername());
    Assert.assertTrue(accountService.signUp(user));  //sign up successfully
    user.setUsername(user.getUsername());    //user has registered. Log in!
    Assert.assertFalse(accountService.signUp(user));


} 


} 
