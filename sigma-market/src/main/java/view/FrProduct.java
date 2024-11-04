package view;

import controller.ProductController;
import model.entities.Product;
import model.exceptions.ProductException;
import view.utils.DecimalInputValidator;
import view.utils.TableUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    private JLabel lblStock;
    private JSpinner spnStock;
    private JLabel lblDescription;
    private JTextArea txtDesc;
    private JLabel lblImgUrl;
    private JTextField edtPrice;
    private JTextArea txtImgUrl;
    private JTextArea txtName;
    private JTextArea txtSKU;

    private ProductController controller;
    private boolean isFormActive;
    private int editingId;

    public FrProduct(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Produtos");
        setSize(1280, 680);

        controller = new ProductController();
        isFormActive = true;
        editingId = -1;

        initCustomComponents();
        swapForm();
        controller.refreshTable(grd);


        edtPrice.addKeyListener(new DecimalInputValidator(edtPrice));
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               editingId = -1;
               swapForm();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editingId = -1;
                swapForm();
                cleanForm();
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product marked = (Product) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para edição.", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                editingId = marked.getId();

                loadForm(marked);
                swapForm();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product marked = (Product) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para exclusão!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null,
                        "Tem certeza que deseja excluir '" + marked.getName() + "'?",
                        "Confirmar exclusão",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    controller.deleteProduct(marked.getId());
                    controller.refreshTable(grd);
                }
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (editingId == -1)
                        controller.createProduct(txtName.getText(), txtDesc.getText(), txtImgUrl.getText(), edtPrice.getText(),(Integer) spnStock.getValue(), txtSKU.getText());
                    else
                        controller.updateProduct(editingId, txtName.getText(), txtDesc.getText(), txtImgUrl.getText(), edtPrice.getText(),(Integer) spnStock.getValue(), txtSKU.getText());

                    controller.refreshTable(grd);
                    swapForm();
                    cleanForm();
                } catch (ProductException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        lblSearchImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.filterTable(grd, edtSearch.getText());
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

    private void swapForm() {
        isFormActive = !isFormActive;

        btnCancel.setVisible(isFormActive);
        btnSalvar.setVisible(isFormActive);
        btnDelete.setVisible(!isFormActive);
        btnEdit.setVisible(!isFormActive);
        btnNew.setVisible(!isFormActive);
        panForm.setVisible(isFormActive);
        panBot.setVisible(!isFormActive);
    }

    private void cleanForm() {
        txtName.setText("");
        txtSKU.setText("");
        spnStock.setValue(0);
        txtDesc.setText("");
        txtImgUrl.setText("");
        edtPrice.setText("");
    }

    private void loadForm(Product o) {
        txtName.setText(o.getName());
        txtSKU.setText(o.getSku());
        spnStock.setValue(o.getStock());
        txtDesc.setText(o.getDescription());
        txtImgUrl.setText(o.getImgUrl());
        edtPrice.setText(String.format("%.2f", o.getPrice()));
    }
}