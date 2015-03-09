package com.railwaycompany.presentation.servlets;

import com.railwaycompany.business.services.implementation.ServiceFactorySingleton;
import com.railwaycompany.business.services.interfaces.ServiceFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MainServletContextListener implements ServletContextListener {

    private static final Logger LOG = Logger.getLogger(MainServletContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            LogManager.getLogManager().readConfiguration(
                    MainServletContextListener.class.getResourceAsStream("/logging.properties"));
            LOG.info("Application starting...");
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        ServiceFactory factory = ServiceFactorySingleton.getInstance();
        if (factory == null) {
            LOG.log(Level.SEVERE, "Cannot create ServiceFactory");
        } else {
            LOG.log(Level.INFO, "ServiceFactory created");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServiceFactory factory = ServiceFactorySingleton.getInstance();
        if (factory != null) {
            factory.close();
            LOG.log(Level.INFO, "ServiceFactory has been closed");
        } else {
            LOG.log(Level.WARNING, "ServiceFactory is null");
        }
        LOG.info("Application has been closed");
    }
}
