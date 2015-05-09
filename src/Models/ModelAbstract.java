package Models;

import Controllers.MainController;

public abstract class ModelAbstract {

    protected MainController controller;

    public ModelAbstract(MainController controller) {
        this.controller = controller;
    }

}
