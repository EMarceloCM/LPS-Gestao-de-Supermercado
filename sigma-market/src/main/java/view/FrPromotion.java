package view;

import controller.PromotionController;
import model.entities.Product;
import model.entities.Promotion;
import model.exceptions.PromotionException;
import view.dialogs.DlgChooseProduct;
import view.utils.DecimalInputValidator;
import view.utils.TableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FrPromotion extends JDialog {
    private JPanel panTop;
    private JLabel lblTitle;
    private JPanel panButtons;
    private JButton btnNew;
    private JButton btnCancel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnSalvar;
    private javax.swing.JScrollPane JScrollPane;
    private JTable grd;
    private JPanel panForm;
    private JTextField edtProductId;
    private JLabel lblDiscount;
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;
    private JFormattedTextField fEdtDiscount;
    private JPanel panMain;
    private JLabel lblDuration;
    private JLabel lblProd;
    private JCheckBox chkActive;
    private JLabel lblActive;
    private JSpinner spnDuration;
    private JLabel lblSearchProd;

    private PromotionController controller;
    private Product selectedProduct;
    private boolean isFormActive;
    private int editingId;
//TODO fazer filtro de pesquisa funcionar
    public FrPromotion(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Promoção");
        setSize(1280, 680);

        controller = new PromotionController();
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
                Promotion marked = (Promotion) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para edição.", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                editingId = marked.getId();

                selectedProduct = marked.getProduct();

                loadForm(marked);
                swapForm();
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (editingId == -1)
                        controller.createPromotion(fEdtDiscount.getText(), spnDuration.getValue().toString(), chkActive.isSelected(), selectedProduct);
                    else
                        controller.updatePromotion(editingId, fEdtDiscount.getText(), spnDuration.getValue().toString(), chkActive.isSelected(), selectedProduct);

                    controller.refreshTable(grd);
                    swapForm();
                    cleanForm();
                } catch (PromotionException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        lblSearchProd.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            DlgChooseProduct dlg = new DlgChooseProduct(null, true);
            dlg.setLocationRelativeTo(FrPromotion.this);
            dlg.setVisible(true);

            selectedProduct = dlg.getSelected();

            if (selectedProduct != null)
                edtProductId.setText(selectedProduct.getName() + " (" + selectedProduct.getId() +  ")");
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Promotion marked = (Promotion) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para exclusão!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null,
                        "Tem certeza que deseja excluir a promoção '" + marked.getProduct().getName() + " (" + marked.getDiscountPercentage() + "%) '?",
                        "Confirmar exclusão",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    controller.deletePromotion(marked.getId());
                    controller.refreshTable(grd);
                }
            }
        });
    }

    private void initCustomComponents() {
        // set clickable buttons
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblSearchImg.setCursor(hand);
        lblSearchProd.setCursor(hand);

        fEdtDiscount.addKeyListener(new DecimalInputValidator(fEdtDiscount));

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
        edtProductId.setText("");
        fEdtDiscount.setText("");
        spnDuration.setValue(0);
        chkActive.setSelected(true);

        this.selectedProduct = null;
    }

    private void loadForm(Promotion o) {
        edtProductId.setText(o.getProduct().getName() + " (" + o.getId() +  ")");
        fEdtDiscount.setText(String.format("%.2f", o.getDiscountPercentage()).replace(',', '.'));
        spnDuration.setValue(o.getDurationMinutes());
        chkActive.setSelected(o.getActive() == 1);
    }
}
