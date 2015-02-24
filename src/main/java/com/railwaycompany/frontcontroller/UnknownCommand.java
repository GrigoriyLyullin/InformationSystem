package com.railwaycompany.frontcontroller;

import com.railwaycompany.utils.PageGenerator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UnknownCommand implements Command {

    private String commandStr;

    public UnknownCommand(String commandStr) {
        this.commandStr = commandStr;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.getWriter().write(PageGenerator.getError("UnknownCommand " + commandStr));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
