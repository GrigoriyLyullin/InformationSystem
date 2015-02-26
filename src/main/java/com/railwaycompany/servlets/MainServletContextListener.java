package com.railwaycompany.servlets;

import com.railwaycompany.services.ServiceFactorySingleton;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MainServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServiceFactorySingleton.getInstance().close();
    }
}
