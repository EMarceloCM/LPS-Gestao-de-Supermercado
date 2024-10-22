package view;

import controller.CustomerController;
import model.entities.Customer;
import model.enums.Role;
import model.exceptions.CustomerException;
import model.utils.TableUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class FrCustomer extends JDialog {
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
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;
    private JPanel panFormCore;
    private JLabel lblPassword;
    private JLabel lblEmail;
    private JTextField edtCnpj;
    private JPanel panMain;
    private JLabel lblNome;
    private JTextField edtNome;
    private JLabel lblCpf;
    private JTextField edtCpf;
    private JPasswordField pswUserPassword;
    private JComboBox comboBoxRole;
    private JLabel lblRole;
    private JTextField edtEmail;

    private CustomerController controller;
    boolean isFormActive;
    int editingId;


    public FrCustomer(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Usuario");
        setSize(1280, 680);

        controller = new CustomerController();
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
                Customer marked = (Customer) TableUtils.getObjectSelected(grd);
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
                Customer marked = (Customer) TableUtils.getObjectSelected(grd);
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
                    controller.deleteCustomer(marked.getId());
                    controller.refreshTable(grd);
                }
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (editingId == -1)
                        controller.createCustomer(edtCpf.getText(), edtEmail.getText(), edtNome.getText(), new String(pswUserPassword.getPassword()), comboBoxRole.getSelectedIndex());
                    else
                        controller.updateCustomer(editingId, edtCpf.getText(), edtEmail.getText(), edtNome.getText(), new String(pswUserPassword.getPassword()), comboBoxRole.getSelectedIndex());

                    controller.refreshTable(grd);
                    swapForm();
                    cleanForm();
                } catch (CustomerException ex) {
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

        // fill combobox with the enum string
        for (Role role : Role.values()) {
            comboBoxRole.addItem(role.name());
        }

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
        edtCpf.setText("");
        edtNome.setText("");
        edtEmail.setText("");
        pswUserPassword.setText("");
        comboBoxRole.setSelectedIndex(0);
    }

    private void loadForm(Customer o) {
        edtCpf.setText(o.getCpf());
        edtNome.setText(o.getName());
        edtEmail.setText(o.getEmail());
        comboBoxRole.setSelectedIndex(o.getRole().ordinal());
    }

}