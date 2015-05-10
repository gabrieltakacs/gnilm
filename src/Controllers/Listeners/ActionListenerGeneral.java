package Controllers.Listeners;

import Controllers.MainController;
import java.awt.event.ActionListener;

public abstract class ActionListenerGeneral implements ActionListener {

    protected MainController controller;

    public ActionListener setController(MainController controller) {
        this.controller = controller;
        return this;
    }

}
