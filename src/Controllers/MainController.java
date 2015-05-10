package Controllers;

import Controllers.Listeners.RunDialogActionListener;
import Views.MainView;

public class MainController extends ControllerAbstract {

    private MainView view;

    public MainController(MainView view) {
        this.view = view;
        this.view.setVisible(true);

        view.bindRunActionListener(new RunDialogActionListener().setController(this));
    }

    public void addLineToDisaggregationOutput(String line, boolean logToConsole) {
        this.view.addLineToDisaggregationOutput(line + "\n");

        if (logToConsole) {
            this.addLineToLog(line);
        }
    }

    public void addLineToEnergyCalculatorOutput(String line, boolean logToConsole) {
        this.view.addLineToRecommendationsOutput(line + "\n");

        if (logToConsole) {
            this.addLineToLog(line);
        }
    }

    public void addLineToLog(String line) {
        this.view.addLineToLog(line + "\n");
        System.out.println(line);
    }

}
