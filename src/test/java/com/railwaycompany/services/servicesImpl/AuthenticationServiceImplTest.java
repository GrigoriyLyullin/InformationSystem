package com.railwaycompany.services.servicesImpl;

import com.railwaycompany.dao.abstractDao.UserDao;
import com.railwaycompany.services.abstractServices.AuthenticationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class AuthenticationServiceImplTest {

    private static final String SESSION_ID = "testSessionId";
    private static final String LOGIN = "testLogin";
    private static final String PASSWORD = "testPassword";

    private AuthenticationService authenticationService;

    @Before
    public void setUp() throws Exception {
        UserDao userDao = Mockito.mock(UserDao.class);
        authenticationService = new AuthenticationServiceImpl(userDao);
    }

    @Test
    public void testIsAuthorized() throws Exception {
        String authId = authenticationService.signIn(SESSION_ID, LOGIN, PASSWORD);
        Assert.assertNotNull(authId);
        Assert.assertTrue(authenticationService.isAuthorized(SESSION_ID, authId));

        String fakeAuthId = new StringBuffer(authId).reverse().toString();
        String fakeSessionId = new StringBuffer(SESSION_ID).reverse().toString();
        Assert.assertFalse(authenticationService.isAuthorized(SESSION_ID, fakeAuthId));
        Assert.assertFalse(authenticationService.isAuthorized(fakeSessionId, authId));
        Assert.assertFalse(authenticationService.isAuthorized(fakeSessionId, fakeAuthId));
    }

    @Test
    public void testSignIn() throws Exception {
        String authId = authenticationService.signIn(SESSION_ID, LOGIN, PASSWORD);
        Assert.assertNotNull(authId);
        String notExistLogin = "notExistLogin";
        String notExistPassword = "notExistPassword";
        authId = authenticationService.signIn(SESSION_ID, notExistLogin, notExistPassword);
        Assert.assertNull(authId);
    }

    @Test
    public void testSignOut() throws Exception {
        String authId = authenticationService.signIn(SESSION_ID, LOGIN, PASSWORD);
        Assert.assertTrue(authenticationService.isAuthorized(SESSION_ID, authId));
        authenticationService.signOut(SESSION_ID);
        Assert.assertFalse(authenticationService.isAuthorized(SESSION_ID, authId));
    }

    @Test
    public void testGetUserId() throws Exception {
        String authId = authenticationService.signIn(SESSION_ID, LOGIN, PASSWORD);
        Assert.assertNotNull(authenticationService.getUserId(SESSION_ID, authId));
        String fakeAuthId = new StringBuffer(authId).reverse().toString();
        Assert.assertNull(authenticationService.getUserId(SESSION_ID, fakeAuthId));
    }
}