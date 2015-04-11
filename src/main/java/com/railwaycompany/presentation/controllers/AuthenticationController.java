package com.railwaycompany.presentation.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthenticationController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public final ModelAndView login(@RequestParam(value = "msg", required = false) final String msg,
                                    @RequestParam(value = "error", required = false) final String error) {
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("signInError", true);
        } else if (msg != null) {
            model.addObject("signInMessage", true);
        }
        model.setViewName("login");
        return model;
    }

}
