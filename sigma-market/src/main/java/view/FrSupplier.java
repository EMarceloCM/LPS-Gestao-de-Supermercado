package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel panForm;
    private JLabel lblNome;
    private JTextField edtNome;
    private JLabel lblCnpj;
    private JTextField edtCnpj;
    private JButton btnSalvar;

    private boolean isFormActive;

    public FrSupplier(Frame parent, boolean modal) {
        super(parent, modal);

        isFormActive = true;
        swapForm();

        // window info
        setContentPane(panMain);
        setTitle("Fornecedor");
        setSize(1280, 680);
        btnNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapForm();
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapForm();
                cleanForm();
            }
        });
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapForm();
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapForm();

                cleanForm();
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
}