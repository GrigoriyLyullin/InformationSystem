package com.railwaycompany.utils;

import java.io.*;
import java.util.logging.Logger;

public class PageGenerator {

    private static Logger log = Logger.getLogger(PageGenerator.class.getName());

    public static String getError(String message) {

        StringBuilder errorPage = getPage("error.html");

        int start = errorPage.indexOf("%message%");
        int end = start + "%message%".length();
        errorPage = errorPage.replace(start, end, message);

        return errorPage.toString();
    }

    private static StringBuilder getPage(String page) {

        StringBuilder pageStr = new StringBuilder();

        File pageFile = new File("C:\\Users\\Grigoriy\\IdeaProjects\\InformationSystem\\src\\main\\webapp\\" + page);

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader(pageFile));

            String current;
            while ((current = reader.readLine()) != null) {
                pageStr.append(current);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return pageStr;
    }
}
