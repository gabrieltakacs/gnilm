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
    private JTextField highTariffStartField;
    private JTextField highTariffEndField;

    public GeneralConfigurationView() {
        setContentPane(contentPane);
        setTitle("GNILM: Disaggregation settings");
        setSize(400, 200);
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
        Configuration configuration = Configuration.getInstance();

        this.defaultWindowDetectionThresholdInput.setText(configuration.getDefaultEventDetectionThreshold().toString());
        this.deltaBoostConstantInput.setText(configuration.getDeltaBoostConstant().toString());
        this.highTariffStartField.setText(configuration.getHighTariffZoneFrom());
        this.highTariffEndField.setText(configuration.getHighTariffZoneUntil());
    }

    private void saveValues() {
        Configuration configuration = Configuration.getInstance();

        configuration.setDeltaBoostConstant(Double.parseDouble(this.deltaBoostConstantInput.getText()));
        configuration.setDefaultEventDetectionThreshold(Double.parseDouble(this.defaultWindowDetectionThresholdInput.getText()));
        configuration.setHighTariffZoneFrom(this.highTariffStartField.getText());
        configuration.setHighTariffZoneUntil(this.highTariffEndField.getText());
    }

    public static void main(String[] args) {
        GeneralConfigurationView dialog = new GeneralConfigurationView();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
