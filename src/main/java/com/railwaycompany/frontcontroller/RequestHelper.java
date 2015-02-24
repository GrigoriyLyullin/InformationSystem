package com.railwaycompany.frontcontroller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class RequestHelper {

    private static final String COMMAND_STR = "Command";

    private static final String PACKAGE_STR = "com.railwaycompany.frontcontroller.";

    private static Logger log = Logger.getLogger(RequestHelper.class.getName());

    private Map<String, Command> commandMap = new HashMap<>();
    private Command command;

    public RequestHelper(HttpServletRequest request) {

        commandMap.put("LoginCommand", new LoginCommand());

        String commandStr = request.getHeader(COMMAND_STR);
        command = commandMap.get(commandStr);

        if (command == null) {
            command = new UnknownCommand(commandStr);
        }
    }

    public Command getCommand() {
        return command;
    }
}
