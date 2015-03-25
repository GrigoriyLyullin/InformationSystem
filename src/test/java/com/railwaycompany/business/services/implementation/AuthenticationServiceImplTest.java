package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.services.interfaces.AuthenticationService;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthenticationServiceImplTest {

    private static final String SESSION_ID = "testSessionId";
    private static final int USER_ID = 1;
    private static final String USER_LOGIN = "testLogin";
    private static final String USER_PASSWORD = "testPassword";
    private static final String USER_PASSWORD_HASH = "fed3b61b26081849378080b34e693d2e";

    private AuthenticationService authenticationService;

    @Before
    @Ignore
    public void setUp() throws Exception {

        User user = new User();
        user.setId(USER_ID);
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD_HASH);
//        user.setEmployee(false);

        UserDao userDao = mock(UserDao.class);

//        when(userDao.findUser(USER_LOGIN, USER_PASSWORD_HASH)).thenReturn(user);

        authenticationService = new AuthenticationServiceImpl();
    }

    @Test
    @Ignore
    public void testIsAuthorized() throws Exception {
//        String authId = authenticationService.signIn(SESSION_ID, USER_LOGIN, USER_PASSWORD);
//        Assert.assertNotNull(authId);
//        Assert.assertTrue(authenticationService.isAuthorized(SESSION_ID, authId));
//
//        String fakeAuthId = new StringBuffer(authId).reverse().toString();
//        String fakeSessionId = new StringBuffer(SESSION_ID).reverse().toString();
//        Assert.assertFalse(authenticationService.isAuthorized(SESSION_ID, fakeAuthId));
//        Assert.assertFalse(authenticationService.isAuthorized(fakeSessionId, authId));
//        Assert.assertFalse(authenticationService.isAuthorized(fakeSessionId, fakeAuthId));
    }

    @Test
    @Ignore
    public void testSignIn() throws Exception {
//        String authId = authenticationService.signIn(SESSION_ID, USER_LOGIN, USER_PASSWORD);
//        Assert.assertNotNull(authId);
//        String notExistLogin = "notExistLogin";
//        String notExistPassword = "notExistPassword";
//        authId = authenticationService.signIn(SESSION_ID, notExistLogin, notExistPassword);
//        Assert.assertNull(authId);
    }

    @Test
    @Ignore
    public void testSignOut() throws Exception {
//        String authId = authenticationService.signIn(SESSION_ID, USER_LOGIN, USER_PASSWORD);
//        Assert.assertTrue(authenticationService.isAuthorized(SESSION_ID, authId));
//        authenticationService.signOut(SESSION_ID);
//        Assert.assertFalse(authenticationService.isAuthorized(SESSION_ID, authId));
    }

    @Test
    @Ignore
    public void testGetUserId() throws Exception {
//        String authId = authenticationService.signIn(SESSION_ID, USER_LOGIN, USER_PASSWORD);
//        Assert.assertNotNull(authenticationService.getUserId(SESSION_ID, authId));
//        String fakeAuthId = new StringBuffer(authId).reverse().toString();
//        Assert.assertNull(authenticationService.getUserId(SESSION_ID, fakeAuthId));
    }
}