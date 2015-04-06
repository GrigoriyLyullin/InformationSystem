package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.services.interfaces.AuthenticationService;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import com.railwaycompany.persistence.entities.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.Collection;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class AuthenticationServiceImplTest {

    private static final int USER_ID = 1;
    private static final String USER_LOGIN = "testLogin";
    private static final String NOT_EXIST_USER_LOGIN = "testNotExistLogin";
    private static final String USER_PASSWORD = "testPassword";
    private static final String USER_ROLE = "ROLE_USER";

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    public void testLoadUserByUsername() throws Exception {
        UserDetails userDetails = authenticationService.loadUserByUsername(USER_LOGIN);
        Assert.assertEquals(userDetails.getUsername(), USER_LOGIN);
        Assert.assertEquals(userDetails.getPassword(), USER_PASSWORD);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            Assert.assertEquals(authority.getAuthority(), USER_ROLE);
        }
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByNotExistUsername() throws Exception {
        authenticationService.loadUserByUsername(NOT_EXIST_USER_LOGIN);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByEmptyUsername() throws Exception {
        authenticationService.loadUserByUsername("");
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testLoadUserByNullUsername() throws Exception {
        authenticationService.loadUserByUsername(null);
    }

    @Configuration
    static class ContextConfiguration {

        @Bean
        public AuthenticationService authenticationService() {
            return new AuthenticationServiceImpl();
        }

        @Bean
        public UserDao userDao() {
            UserDao userDao = mock(UserDao.class);
            User user = new User();
            user.setId(USER_ID);
            user.setLogin(USER_LOGIN);
            user.setPassword(USER_PASSWORD);

            UserRole userRole = new UserRole();
            userRole.setRole(USER_ROLE);
            userRole.setUser(user);

            user.setUserRole(userRole);

            when(userDao.findUser(USER_LOGIN)).thenReturn(user);
            when(userDao.findUser(NOT_EXIST_USER_LOGIN)).thenReturn(null);

            return userDao;
        }
    }
}