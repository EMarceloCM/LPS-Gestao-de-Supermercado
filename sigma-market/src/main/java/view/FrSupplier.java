package view;

import controller.ProductController;
import controller.SupplierController;
import model.entities.Product;
import model.entities.Supplier;
import model.utils.TableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                    JOptionPane.showMessageDialog(null, "Selecione um registro para edição", "Erro!", JOptionPane.ERROR_MESSAGE);
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
                if (editingId == -1)
                    supplierController.createSupplier(edtCnpj.getText(), edtNome.getText());
                else
                    supplierController.updateSupplier(editingId,edtCnpj.getText(), edtNome.getText());

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
    }

    private void swapForm() {
        isFormActive = !isFormActive;

        btnCancel.setVisible(isFormActive);
        btnSalvar.setVisible(isFormActive);

        btnDelete.setVisible(!isFormActive);
        btnEdit.setVisible(!isFormActive);
        btnNew.setVisible(!isFormActive);

        panForm.setVisible(isFormActive);
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