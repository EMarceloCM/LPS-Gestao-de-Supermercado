package view;

import controller.SupplierController;
import model.entities.Supplier;
import model.exceptions.SupplierException;
import model.utils.TableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrSupplier extends JDialog {

    private JPanel panMain;
    private JTable grd;
    private JPanel panButtons;
    private JPanel panTop;
    private JLabel lblTitle;
    private JButton btnNew;
    private JButton btnCancel;
    private JButton btnEdit;
    private JButton btnDelete;
    private JPanel panForm;
    private JLabel lblNome;
    private JTextField edtNome;
    private JLabel lblCnpj;
    private JTextField edtCnpj;
    private JButton btnSalvar;
    private javax.swing.JScrollPane JScrollPane;
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;

    private SupplierController supplierController;
    private boolean isFormActive;
    private int editingId;

    public FrSupplier(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Fornecedor");
        setSize(1280, 680);

        supplierController = new SupplierController();
        isFormActive = true;
        initCustomComponents();
        swapForm();
        supplierController.refreshTable(grd);

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
                Supplier marked = (Supplier) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para edição.", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                editingId = marked.getId();

                loadForm(marked);
                swapForm();
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (editingId == -1)
                        supplierController.createSupplier(edtCnpj.getText(), edtNome.getText());
                    else
                        supplierController.updateSupplier(editingId, edtCnpj.getText(), edtNome.getText());
                } catch (SupplierException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }

                supplierController.refreshTable(grd);
                swapForm();
                cleanForm();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Supplier marked = (Supplier) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para exclusão!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null,
                        "Tem certeza que deseja excluir o fornecedor '" + marked.getName() + "'?",
                        "Confirmar exclusão",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    supplierController.deleteSupplier(marked.getId());
                    supplierController.refreshTable(grd);
                }
            }
        });
        lblSearchImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
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
        edtCnpj.setText("");
        edtNome.setText("");
    }

    private void loadForm(Supplier o) {
        edtCnpj.setText(o.getCnpj());
        edtNome.setText(o.getName());
    }
}