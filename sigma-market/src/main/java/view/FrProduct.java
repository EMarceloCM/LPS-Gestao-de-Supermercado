package view;

import controller.CustomerController;
import model.utils.FormatterUtil;

import javax.swing.*;
import java.awt.*;

public class FrProduct extends  JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private JPanel panButtons;
    private JButton btnNew;
    private JButton btnCancel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSalvar;
    private JPanel panForm;
    private JPanel panFormCore;
    private JLabel lblexpDate;
    private JLabel lblName;
    private JTextField edtName;
    private JLabel lblSKU;
    private JLabel lblPrice;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grd;
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;
    private JPanel panMain;
    private JFormattedTextField fEdtData;
    private JTextField edtSKU;
    private JFormattedTextField fEdtPrice;
    private JLabel lblStock;
    private JSpinner spnStock;
    private JLabel lblDescription;
    private JTextArea textArea1;
    private JLabel lblImgUrl;
    private JTextField edtImgUrl;

    public FrProduct(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Produtos");
        setSize(1280, 680);

        FormatterUtil.applyDateMask(fEdtData);
    }
}
