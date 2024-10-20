package view;

import javax.swing.*;
import java.awt.*;

public class FrSupplier extends JDialog {

    private JPanel panMain;
    private JTable table1;
    private JPanel panButtons;
    private JPanel panTop;
    private JLabel lblTitle;
    private JButton btnNew;
    private JButton btnCancel;
    private JButton btnEdit;
    private JButton btnDelete;

    public FrSupplier(Frame parent, boolean modal) {
        super(parent, modal);

        // window info
        setContentPane(panMain);
        setTitle("Fornecedor");
        setSize(1280, 680);
    }
}