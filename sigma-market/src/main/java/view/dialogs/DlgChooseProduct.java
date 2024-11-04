package view.dialogs;

import controller.ProductController;
import lombok.Getter;
import model.entities.Product;
import view.utils.TableUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DlgChooseProduct extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grd;
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;
    private JPanel panMain;
    private JButton btnConfirm;
    private JPanel panSearchForm;

    private ProductController controller;
    @Getter
    private Product selected;

    public DlgChooseProduct(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Produtos");
        setSize(500, 700);

        controller = new ProductController();
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
                Product marked = (Product) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um produto!", "Erro!", JOptionPane.ERROR_MESSAGE);
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