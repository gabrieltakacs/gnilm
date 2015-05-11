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

    public void addInfo(String message, Boolean displayModal) {
        this.log("[I] " + message);

        if (displayModal) {
            this.controller.displayInfoModal(message);
        }
    }

    public void addWarning(String message, Boolean displayModal) {
        this.log("[W] " + message);

        if (displayModal) {
            this.controller.displayWarningModal(message);
        }
    }

    public void addFatalError(String message) {
        this.log("[F] " + message);
        this.controller.displayFatalModal(message);
    }


}
