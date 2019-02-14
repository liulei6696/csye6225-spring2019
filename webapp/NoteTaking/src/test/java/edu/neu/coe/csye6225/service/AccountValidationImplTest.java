package edu.neu.coe.csye6225.service;

import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountValidationImplTest {

    @Autowired
    private AccountValidation accountValidation;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserMapper userMapper;

    @Test
    public void nameValidation() {
        Assert.assertFalse(accountValidation.nameValidation("aaaa123"));
        Assert.assertFalse(accountValidation.nameValidation("AA!@qqqq"));
        Assert.assertFalse(accountValidation.nameValidation("12345@zxc"));
        Assert.assertTrue(accountValidation.nameValidation("12345@zxc.com"));
    }

    @Test
    public void isPasswordStrong() {
        Assert.assertFalse(accountValidation.isPasswordStrong("aaaaaa"));
        Assert.assertFalse(accountValidation.isPasswordStrong("AA12345"));
        Assert.assertTrue(accountValidation.isPasswordStrong("12345!@zxc"));
    }

    @Test
    public void passwordEncrypt() {
        Assert.assertNotEquals("12345!@zxc",accountValidation.passwordEncrypt("12345!@zxc"));
    }

    @Test
    @Transactional
    public void isUserRegistered() {
        User user = new User();
        user.setUsername("12345777@qq.com");
        user.setPassword("123@#$qer");
        if(userMapper.getUserByUsername(user.getUsername())!=null)
            userMapper.deleteUserByUsername(user.getUsername());
        assertFalse(accountValidation.isUserRegistered(user));
        accountService.signUp(user);
        assertTrue(accountValidation.isUserRegistered(user));

    }

    @Test
    @Transactional
    public void isPasswordCorrect() {
        User user = new User();
        user.setUsername("12345777@qq.com");
        if(userMapper.getUserByUsername(user.getUsername())!=null)
            userMapper.deleteUserByUsername(user.getUsername());
        user.setPassword("123@#$qer");
        accountService.signUp(user);
        assertTrue(accountValidation.isPasswordCorrect(user));
        user.setPassword("123@qqq");
        assertFalse(accountValidation.isPasswordCorrect(user));

    }
}