package view;

import controller.AddressController;
import model.entities.Address;
import model.entities.Customer;
import model.exceptions.PromotionException;
import view.dialogs.DlgChooseCustomer;
import view.utils.FormatterUtils;
import view.utils.TableUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrAddress extends JDialog {
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
    private JLabel lblStreet;
    private JTextField edtStreet;
    private JLabel lblCnpj;
    private JPanel panBot;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;
    private JPanel panMain;
    private JFormattedTextField fEdtNum;
    private JLabel lblComplement;
    private JLabel lblNeighbourhood;
    private JTextField edtComplement;
    private JTextField edtNeigh;
    private JLabel lblZip;
    private JFormattedTextField fEdtZip;
    private JLabel lblUser;
    private JTextField edtUser;
    private JLabel lblSearchUsr;

    private AddressController controller;
    boolean isFormActive;
    int editingId;
    private Customer selectedCustomer;

    public FrAddress(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Endereço");
        setSize(1280, 680);

        controller = new AddressController();
        isFormActive = true;
        initCustomComponents();
        swapForm();
        // TODO fazer o campo número aceitar apenas números, está crashando qnd coloca letras
        // TODO fazer campo de pesquisa funcionar
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
                Address marked = (Address) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para edição.", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                editingId = marked.getId();

                selectedCustomer = marked.getCustomer();

                loadForm(marked);
                swapForm();
            }
        });
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try { //TODO se o usuario logado for customer, pegar o id da sessao dele e nao exibir a pesquisa de usuarios
                    if (editingId == -1)
                        controller.createAddress(edtStreet.getText(), fEdtNum.getText(), edtComplement.getText(), edtNeigh.getText(), fEdtZip.getText(), selectedCustomer);
                    else
                        controller.updateAddress(editingId, edtStreet.getText(), fEdtNum.getText(), edtComplement.getText(), edtNeigh.getText(), fEdtZip.getText(), selectedCustomer);

                    controller.refreshTable(grd);
                    swapForm();
                    cleanForm();
                } catch (PromotionException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Address marked = (Address) TableUtils.getObjectSelected(grd);
                if (marked == null) {
                    JOptionPane.showMessageDialog(null, "Selecione um registro para exclusão!", "Erro!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int response = JOptionPane.showConfirmDialog(null,
                        "Tem certeza que deseja excluir o endereço '" + marked.getStreet() + ", " + marked.getNumber() + "'?",
                        "Confirmar exclusão",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.OK_OPTION) {
                    controller.deleteAddress(marked.getId());
                    controller.refreshTable(grd);
                }
            }
        });
        lblSearchUsr.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            DlgChooseCustomer dlg = new DlgChooseCustomer(null, true);
            dlg.setLocationRelativeTo(FrAddress.this);
            dlg.setVisible(true);

            selectedCustomer = dlg.getSelected();

            if (selectedCustomer != null)
                edtUser.setText(selectedCustomer.getName() + " (" + selectedCustomer.getId() + ")");
            }
        });
    }

    private void initCustomComponents() {
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblSearchImg.setCursor(hand);
        lblSearchUsr.setCursor(hand);

        FormatterUtils.applyCepMask(fEdtZip);

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
        edtStreet.setText("");
        fEdtNum.setText("");
        edtComplement.setText("");
        edtNeigh.setText("");
        fEdtZip.setText("");
        edtUser.setText("");

        selectedCustomer = null;
    }

    private void loadForm(Address o) {
        edtStreet.setText(o.getStreet());
        fEdtNum.setText(String.valueOf(o.getNumber()));
        edtComplement.setText(o.getComplement());
        edtNeigh.setText(o.getNeighborhood());
        fEdtZip.setText(o.getZipcode());
        edtUser.setText(o.getCustomer().getName() + " (" + o.getCustomer().getId() + ")");
    }
}
