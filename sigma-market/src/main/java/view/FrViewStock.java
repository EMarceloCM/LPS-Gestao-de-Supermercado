package view;

import controller.ProductSupplierController;
import controller.SupplierController;
import model.entities.Product;
import model.entities.ProductSupplier;
import model.entities.Promotion;
import model.entities.Supplier;
import model.exceptions.ProductSupplierException;
import model.exceptions.PromotionException;
import view.dialogs.DlgChooseProduct;
import view.dialogs.DlgChooseSupplier;
import view.utils.DecimalInputValidator;
import view.utils.TableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    private ProductSupplierController controller;
    private boolean isFormActive;
    private int editingId;

    private Product selectedProduct;
    private Supplier selectedSupplier;

    public FrViewStock(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Estoque");
        setSize(1280, 680);

        controller = new ProductSupplierController();
        selectedSupplier = null;
        selectedProduct = null;

        isFormActive = true;
        initCustomComponents();
        swapForm();
        controller.refreshTable(grd);


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
                ProductSupplier marked = (ProductSupplier) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para edição.", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                editingId = marked.getId();

                selectedProduct = marked.getProduct();
                selectedSupplier = marked.getSupplier();

                loadForm(marked);
                swapForm();
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (editingId == -1)
                        controller.createProductSupplier(selectedProduct, selectedSupplier, fEdtTotalPrice.getText(), spnQuantity.getValue().toString());
                    else
                        controller.updateProductSupplier(editingId, selectedProduct, selectedSupplier, fEdtTotalPrice.getText(), spnQuantity.getValue().toString());

                    controller.refreshTable(grd);
                    swapForm();
                    cleanForm();
                } catch (ProductSupplierException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        lblSearchProd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DlgChooseProduct dlg = new DlgChooseProduct(null, true);
                dlg.setLocationRelativeTo(FrViewStock.this);
                dlg.setVisible(true);

                selectedProduct = dlg.getSelected();

                if (selectedProduct != null)
                    edtProduct.setText(selectedProduct.getName());
            }
        });
        lblSearchSup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DlgChooseSupplier dlg = new DlgChooseSupplier(null, true);
                dlg.setLocationRelativeTo(FrViewStock.this);
                dlg.setVisible(true);

                selectedSupplier = dlg.getSelected();

                if (selectedSupplier != null)
                    edtSupplier.setText(selectedSupplier.getName());
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductSupplier marked = (ProductSupplier) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para exclusão!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null,
                        "Tem certeza que deseja excluir esse estoque?",
                        "Confirmar exclusão",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    controller.deleteProductSupplier(marked.getId());
                    controller.refreshTable(grd);
                }
            }
        });
    }

    private void initCustomComponents() {
        // set clickable buttons
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblSearchProd.setCursor(hand);
        lblSearchSup.setCursor(hand);
        lblSearchImg.setCursor(hand);

        fEdtTotalPrice.addKeyListener(new DecimalInputValidator(fEdtTotalPrice));

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
        edtProduct.setText("");
        edtSupplier.setText("");
        fEdtTotalPrice.setText("");
        spnQuantity.setValue(0);

        selectedSupplier = null;
        selectedProduct = null;
    }

    private void loadForm(ProductSupplier o) {
        edtProduct.setText(o.getProduct().getName());
        edtSupplier.setText(o.getSupplier().getName());
        fEdtTotalPrice.setText(String.format("%.2f", o.getPurchasePrice()).replace(',', '.'));
        spnQuantity.setValue(o.getQuantity());
    }
}
