package com.railwaycompany.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public final ModelAndView login(@RequestParam(value = "error", required = false) final String error) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("signInError", true);
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public final ModelAndView logout(final HttpSession session) {
        session.invalidate();
        return new ModelAndView("index");
    }
}
