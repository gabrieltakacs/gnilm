package Controllers;

import Controllers.Listeners.RunDialogActionListener;
import Utils.Log;
import Views.MainView;

public class MainController extends ControllerAbstract {

    private MainView view;

    public MainController(MainView view) {
        this.view = view;
        this.view.setVisible(true);

        Log.getInstance().setController(this);

        view.bindRunActionListener(new RunDialogActionListener().setController(this));
    }

    public void addLineToDisaggregationOutput(String line, boolean logToConsole) {
        this.view.addLineToDisaggregationOutput(line + "\n");

        if (logToConsole) {
            this.addLineToLog(line);
        }
    }

    public void addLineToEnergyCalculatorOutput(String line, boolean logToConsole) {
        this.view.addLineToEnergyCalculatorOutput(line + "\n");

        if (logToConsole) {
            this.addLineToLog(line);
        }
    }

    public void addLineToLog(String line) {
        this.view.addLineToLog(line + "\n");
        System.out.println(line);
    }

}
