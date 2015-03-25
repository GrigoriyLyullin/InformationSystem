package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.dto.UserData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Controller
@RequestMapping("index")
public class IndexPageServlet {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getIndexPage(HttpSession session) {
        if (session.getAttribute("userData") == null) {
            UserData userData = null;
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                userData = new UserData();
                userData.setUsername(userDetails.getUsername());
                Iterator<? extends GrantedAuthority> iterator = userDetails.getAuthorities().iterator();
                while (iterator.hasNext()) {
                    GrantedAuthority authority = iterator.next();
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        userData.setRole("ROLE_ADMIN");
                        break;
                    } else if (authority.getAuthority().equals("ROLE_USER")) {
                        userData.setRole("ROLE_USER");
                        break;
                    }
                }
            }
            session.setAttribute("userData", userData);
        }
        return new ModelAndView("index");
    }
}
