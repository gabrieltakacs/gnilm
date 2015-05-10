package Utils;

import Controllers.MainController;

public class Log {

    private static Log instance = null;

    MainController controller;

    public static Log getInstance() {
        if (instance == null) {
            instance = new Log();
        }

        return instance;
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

    public void log(String message) {
        controller.addLineToLog(message);
        System.out.println(message);
    }


}
