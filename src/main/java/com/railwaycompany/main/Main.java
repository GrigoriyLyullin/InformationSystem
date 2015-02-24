package com.railwaycompany.main;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class Main implements Runnable, ServletContextListener {

    private static Logger log = Logger.getLogger(Main.class.getName());
    private Thread mainThread;

    @Override
    public void run() {
        while (true) {
            log.info("Hello from main");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        mainThread = new Thread(new Main());
        mainThread.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        mainThread.interrupt();
    }
}
