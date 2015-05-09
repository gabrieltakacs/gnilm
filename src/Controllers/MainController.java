package Controllers;

import Controllers.Listeners.RunActionListener;
import Views.MainView;

public class MainController extends ControllerAbstract {

    private MainView view;

    public MainController(MainView view) {
        this.view = view;
        this.view.setVisible(true);

        view.bindRunActionListener(new RunActionListener().setController(this));
    }

    public void addLineToDisaggregationOutput(String line, boolean logToConsole) {
        this.view.addLineToDisaggregationOutput(line);

        if (logToConsole) {
            this.addLineToLog(line);
        }
    }

    public void addLineToEnergyCalculatorOutput(String line, boolean logToConsole) {
        this.view.addLineToRecommendationsOutput(line);

        if (logToConsole) {
            this.addLineToLog(line);
        }
    }

    public void addLineToLog(String line) {
        this.view.addLineToLog(line);
        System.out.println(line);
    }

}
