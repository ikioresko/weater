package com.weater.service;

import com.weater.model.Role;
import com.weater.model.User;
import com.weater.repos.UserRepo;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private MailSender mailSender;

    @MockBean
    private PasswordEncoder encoder;

    @Test
    public void addUserTest() {
        User user = new User();
        user.setEmail("email@email.com");
        boolean isUserCreated = userService.addUser(user);
        Assert.assertTrue(isUserCreated);
        Assert.assertNotNull(user.getActivationCode());
        Assert.assertTrue(CoreMatchers.is(user.getRoles()).matches(Collections.singleton(Role.USER)));
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
        Mockito.verify(mailSender, Mockito.times(1)).send(
                ArgumentMatchers.eq(user.getEmail()),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.contains("Welcome to Weater"));
    }

    @Test
    public void addUserFailTest() {
        User user = new User();
        user.setUsername("John");
        Mockito.doReturn(user)
                .when(userRepo)
                .findByUsername("John");
        boolean isUserCreated = userService.addUser(user);
        Assert.assertFalse(isUserCreated);
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(mailSender, Mockito.times(0)).send(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString());
    }

    @Test
    public void activateUserTest() {
        User user = new User();
        user.setActivationCode("code!");
        Mockito.doReturn(user)
                .when(userRepo)
                .findByActivationCode("code");
        boolean isUserActivate = userService.activateUser("code");
        Assert.assertTrue(isUserActivate);
        Assert.assertNull(user.getActivationCode());
        Mockito.verify(userRepo, Mockito.times(1)).save(user);
    }

    @Test
    public void activateUSerFailTest() {
        boolean isUserActivate = userService.activateUser("code");
        Assert.assertFalse(isUserActivate);
        Mockito.verify(userRepo, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
    }
}