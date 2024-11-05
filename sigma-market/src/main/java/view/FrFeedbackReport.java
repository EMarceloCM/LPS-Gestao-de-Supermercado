package view;

import Auth.SessionManager;
import controller.CustomerController;

import javax.swing.*;
import java.awt.*;

public class FrFeedbackReport extends JDialog {
    private JLabel lblTitle;
    private JButton btnSalvar;
    private JPanel panForm;
    private JLabel lblInitialDate;
    private JLabel lblFinalDate;
    private JPanel panMain;
    private JFormattedTextField fEdtInitialDate;
    private JFormattedTextField fEdtFinalDate;
    private JPanel panInfo;
    private JLabel lblAverageStars;
    private JPanel panStar;
    private JLabel lblStar1;
    private JLabel lblStar3;
    private JLabel lblStar4;
    private JLabel lblStar5;
    private JLabel lblStar2;

    public FrFeedbackReport(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setSize(740, 420);
    }
}
