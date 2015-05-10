package Controllers.Listeners;

import java.awt.event.ActionEvent;
import Views.RunDialog;

public class RunDialogActionListener extends ActionListenerAbstract {

    @Override
    public void actionPerformed(ActionEvent e) {
        RunDialog runDialog = new RunDialog(this.controller);
        runDialog.setVisible(true);
    }
}
