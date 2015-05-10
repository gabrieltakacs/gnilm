package Controllers.Listeners;

import Controllers.MainController;
import java.awt.event.ActionListener;

public abstract class ActionListenerAbstract implements ActionListener {

    protected MainController controller;

    public ActionListener setController(MainController controller) {
        this.controller = controller;
        return this;
    }

}
