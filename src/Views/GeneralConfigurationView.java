package Views;

import Configuration.Configuration;

import javax.swing.*;
import java.awt.event.*;

public class GeneralConfigurationView extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField deltaBoostConstantInput;
    private JTextField defaultWindowDetectionThresholdInput;

    public GeneralConfigurationView() {
        setContentPane(contentPane);
        setTitle("GNILM: Disaggregation settings");
        setSize(400, 150);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        loadConfiguredValues();

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        this.saveValues();
        dispose();
    }

    private void onCancel() {
        dispose();
    }

    private void loadConfiguredValues() {
        this.defaultWindowDetectionThresholdInput.setText(Configuration.getInstance().getDefaultEventDetectionThreshold().toString());
        this.deltaBoostConstantInput.setText(Configuration.getInstance().getDeltaBoostConstant().toString());
    }

    private void saveValues() {
        Configuration.getInstance().setDeltaBoostConstant(Double.parseDouble(this.deltaBoostConstantInput.getText()));
        Configuration.getInstance().setDefaultEventDetectionThreshold(Double.parseDouble(this.defaultWindowDetectionThresholdInput.getText()));
    }

    public static void main(String[] args) {
        GeneralConfigurationView dialog = new GeneralConfigurationView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
