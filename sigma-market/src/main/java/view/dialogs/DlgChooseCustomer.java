package view.dialogs;

import controller.CustomerController;
import lombok.Getter;
import model.entities.Customer;
import view.utils.TableUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DlgChooseCustomer extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grd;
    private JPanel panBot;
    private JPanel panSearch;
    private JPanel panSearchForm;
    private JLabel lblSearchImg;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JButton btnConfirm;
    private JPanel panMain;

    private CustomerController controller;
    @Getter
    private Customer selected;

    public DlgChooseCustomer(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Selecionar Usuario");
        setSize(500, 700);

        controller = new CustomerController();
        selected = null;

        initCustomComponents();
        controller.refreshTable(grd);
        lblSearchImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            controller.filterTable(grd, edtSearch.getText());
            }
        });
        btnConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer marked = (Customer) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um usuário!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                selected = marked;
                dispose();
            }
        });
    }

    private void initCustomComponents() {
        // set clickable buttons
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblSearchImg.setCursor(hand);

        // set table layout
        grd.setDefaultEditor(Object.class, null);
        grd.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grd.getTableHeader().setReorderingAllowed(false);
    }
}
