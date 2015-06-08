package Views;

import Configuration.Configuration;
import Controllers.Listeners.RunActionListener;
import Controllers.MainController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RunDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField trainDataRangeFromField;
    private JTextField trainDataRangeUntilField;
    private JTextField testDataRangeFromField;
    private JTextField testDataRangeUntilField;
    private JButton ChooseInputDirectoryButton;

    public RunDialog(MainController controller) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setMinimumSize(new Dimension(500, 180));
        setTitle("GNILM: Run disaggregation");
        setDefaultValues();

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveValues();
                dispose();
            }
        });
        buttonOK.addActionListener(new RunActionListener().setController(controller));

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

        ChooseInputDirectoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File("."));
                fileChooser.setDialogTitle("GNILM: Choose input directory...");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setAcceptAllFileFilterUsed(false);
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    Configuration.getInstance().setInputDirectory(fileChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });
    }

    private void onCancel() {
        dispose();
    }

    private void setDefaultValues() {
        Configuration configuration = Configuration.getInstance();

        this.testDataRangeFromField.setText(configuration.getTestDataRangeFrom().toString());
        this.testDataRangeUntilField.setText(configuration.getTestDataRangeUntil().toString());
        this.trainDataRangeFromField.setText(configuration.getTrainDataRangeFrom().toString());
        this.trainDataRangeUntilField.setText(configuration.getTrainDataRangeUntil().toString());
    }

    private void saveValues() {
        Configuration configuration = Configuration.getInstance();

        configuration.setTestDataRangeFrom(Integer.parseInt(this.testDataRangeFromField.getText()));
        configuration.setTestDataRangeUntil(Integer.parseInt(this.testDataRangeUntilField.getText()));
        configuration.setTrainDataRangeFrom(Integer.parseInt(this.trainDataRangeFromField.getText()));
        configuration.setTrainDataRangeUntil(Integer.parseInt(this.trainDataRangeUntilField.getText()));
    }

}
