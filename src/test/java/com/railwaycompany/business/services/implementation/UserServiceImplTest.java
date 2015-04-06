package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.dto.UserData;
import com.railwaycompany.business.services.interfaces.UserService;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UserServiceImplTest {

    private static final int USER_ID = 1;
    private static final int NOT_EXIST_USER_ID = 2;
    private static final String USER_LOGIN = "testUserLogin";

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserData() throws Exception {
        UserData userData = userService.getUserData(USER_ID);
        Assert.assertNotNull(userData);
        Assert.assertEquals(userData.getId(), USER_ID);
    }

    @Test
    public void testGetNotExistUserData() throws Exception {
        UserData userData = userService.getUserData(NOT_EXIST_USER_ID);
        Assert.assertNull(userData);
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public UserDao userDao() {
            UserDao userDao = mock(UserDao.class);

            User user = new User();
            user.setId(USER_ID);
            user.setLogin(USER_LOGIN);

            when(userDao.read(USER_ID)).thenReturn(user);
            when(userDao.read(NOT_EXIST_USER_ID)).thenReturn(null);

            return userDao;
        }
    }
}