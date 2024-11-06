package view;

import controller.SupplierController;

import javax.swing.*;
import java.awt.*;

public class FrViewStock extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private JPanel panButtons;
    private JButton btnNew;
    private JButton btnCancel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSalvar;
    private JPanel panForm;
    private JLabel lblTotalPrice;
    private JLabel lblQuantity;
    private JLabel lblSupplier;
    private JTextField edtSupplier;
    private JLabel lblSearchSup;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grd;
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;
    private JPanel panMain;
    private JLabel lblProduct;
    private JTextField edtProduct;
    private JLabel lblSearchProd;
    private JSpinner spnQuantity;
    private JFormattedTextField fEdtTotalPrice;

    public FrViewStock(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Estoque");
        setSize(1280, 680);

        // controller = new Controller();
        // isFormActive = true;
        // initCustomComponents();
        // swapForm();
        // controller.refreshTable(grd);
    }
}
