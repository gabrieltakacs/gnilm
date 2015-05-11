package Controllers;

import Controllers.Listeners.RunDialogActionListener;
import Utils.Log;
import Views.MainView;
import javax.swing.*;

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
    }

    public void displayInfoModal(String message) {
        JOptionPane.showMessageDialog(null, message, "GNILM: Info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayWarningModal(String message) {
        JOptionPane.showMessageDialog(null, message, "GNILM: Warning", JOptionPane.WARNING_MESSAGE);
    }

    public void displayFatalModal(String message) {
        JOptionPane.showMessageDialog(null, message, "GNILM: Error", JOptionPane.ERROR_MESSAGE);
    }

}
