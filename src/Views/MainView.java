package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainView extends JFrame {

    private JPanel rootPanel;
    private JTabbedPane tabbedPane;
    private JTextArea disaggregationOutput;
    private JTextArea logTextArea;
    private JTextArea energyCalculatorOutput;

    // Menu components
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuItemExit;
    private JMenu menuProgram;
    private JMenuItem menuProgramRun;
    private JMenu menuSettings;
    private JMenuItem menuSettingsDisaggregation;

    public MainView() {
        super("GNILM: Energy Disaggregator");
        setContentPane(rootPanel);
        initMenu();
        setMinimumSize(new Dimension(800, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void bindRunActionListener(ActionListener actionListener) {
        menuProgramRun.addActionListener(actionListener);
    }

    public void addLineToDisaggregationOutput(String line) {
        this.disaggregationOutput.append(line);
    }

    public void addLineToEnergyCalculatorOutput(String line) {
        this.energyCalculatorOutput.append(line);
    }

    public void addLineToLog(String line) {
        this.logTextArea.append(line);
    }

    private void initMenu() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuFile.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menuFile);

        menuItemExit = new JMenuItem("Exit");
        menuItemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        menuFile.add(menuItemExit);

        menuProgram = new JMenu("Program");
        menuProgram.setMnemonic(KeyEvent.VK_P);
        menuProgramRun = new JMenuItem("Run");
        menuProgramRun.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));

        menuProgram.add(menuProgramRun);
        menuBar.add(menuProgram);

        menuSettings = new JMenu("Settings");
        menuSettings.setMnemonic(KeyEvent.VK_S);
        menuSettingsDisaggregation = new JMenuItem("Disaggregation");
        menuSettingsDisaggregation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GeneralConfigurationView configurationView = new GeneralConfigurationView();
                configurationView.setVisible(true);
            }
        });
        menuSettings.add(menuSettingsDisaggregation);

        menuBar.add(menuSettings);
        menuBar.setVisible(true);
        setJMenuBar(menuBar);
    }
}
