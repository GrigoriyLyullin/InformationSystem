package com.railwaycompany.business.services.implementation;

import com.railwaycompany.business.services.interfaces.AuthenticationService;
import com.railwaycompany.persistence.dao.interfaces.UserDao;
import com.railwaycompany.persistence.entities.User;
import com.railwaycompany.persistence.entities.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The service implements users authentication on server.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        if (username != null) {
            User user = userDao.findUser(username);
            if (user != null) {
                Set<UserRole> userRoleSet = new HashSet<>();
                userRoleSet.add(user.getUserRole());
                List<GrantedAuthority> authorities = buildUserAuthority(userRoleSet);
                return buildUserForAuthentication(user, authorities);
            } else {
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }
        } else {
            throw new UsernameNotFoundException("Empty or not valid username");
        }
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(
            User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        for (UserRole userRole : userRoles) {
            setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
        }
        return new ArrayList<>(setAuths);
    }
}