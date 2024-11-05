package view;

import Auth.SessionManager;
import controller.CustomerController;

import javax.swing.*;
import java.awt.*;

public class FrSellingReport extends JDialog {
    private JPanel panForm;
    private JLabel lblInitialDate;
    private JLabel lblFinalDate;
    private JFormattedTextField fEdtInitialDate;
    private JFormattedTextField fEdtFinalDate;
    private JButton btnSalvar;
    private JLabel lblTitle;
    private JPanel panInfo;
    private JLabel lblAverageSellValue;
    private JPanel panMain;
    private JLabel lblAverageSellValueNum;
    private JLabel lblAveragePurchasePerDay;
    private JLabel lblAveragePurchasePerDayNum;
    private JLabel lblQtdItemPerSell;
    private JLabel lblQtdItemPerSellNum;
    private JLabel lblTopSellValue;
    private JLabel lblTopSellValueNum;
    private JLabel lblTopSellerProduct;
    private JLabel lblTopSellerProductNum;

    public FrSellingReport(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setSize(740, 420);
    }
}
