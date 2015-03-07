package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.services.interfaces.ServiceFactory;
import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainServletContextListener implements ServletContextListener {

    private static Logger log = Logger.getLogger(MainServletContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServiceFactory factory = ServiceFactorySingleton.getInstance();
        if (factory == null) {
            log.log(Level.SEVERE, "Cannot create ServiceFactory");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServiceFactory factory = ServiceFactorySingleton.getInstance();
        if (factory != null) {
            factory.close();
        }
    }
}
