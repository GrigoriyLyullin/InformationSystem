package com.railwaycompany.frontcontroller;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontServlet extends HttpServlet {

    private static final String ERROR_PAGE = "/error.html";

    private static Logger log = Logger.getLogger(FrontServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String contentType = req.getContentType();
        if (contentType.contains("json")) {

            JSONObject resultJson = new JSONObject();

            resultJson.put("name", "testName");
            resultJson.put("surname", "testSurname");

            String s = new SimpleDateFormat("dd-MM-yyyy").format(new Date()).toString();

            log.info(s);

            resultJson.put("birthdate", s);

            resp.setContentType("application/json");

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            resp.getWriter().write(resultJson.toString());

        } else {
            try {
                RequestHelper helper = new RequestHelper(req);
                Command command = helper.getCommand();
                command.execute(req, resp);
            } catch (Exception e) {
                log.log(Level.WARNING, "", e);
            }
        }
    }

}
