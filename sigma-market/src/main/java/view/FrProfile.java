package view;

import Auth.SessionManager;
import Auth.exceptions.AuthException;
import controller.CustomerController;
import model.entities.Customer;
import model.enums.Role;
import model.exceptions.CustomerException;
import view.utils.FormatterUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class FrProfile extends JDialog{
    private JPanel panTop;
    private JLabel lblWelcome;
    private JPanel panForm;
    private JPanel panFormCore;
    private JLabel lblName;
    private JLabel lblPsw;
    private JLabel lblCpf;
    private JLabel lblEmail;
    private JLabel lblProfile;
    private JPanel panMain;
    private JComboBox<String> cbProfile;
    private JButton btnSave;
    private JTextField edtName;
    private JPasswordField edtPsw;
    private JTextField edtCPF;
    private JTextField edtEmail;
    private JFormattedTextField fEdtCpf;

    private Customer customer;
    private CustomerController controller;

    public FrProfile(Frame parent, boolean modal){
        super(parent, modal);
        this.customer = SessionManager.getLoggedUser();
        controller = new CustomerController();
        setContentPane(panMain);
        setSize(740, 420);
        setMinimumSize(new Dimension(740, 420));
        setMaximumSize(new Dimension(900, 550));
        setTitle("Perfil");
        initCustomComponents();
        LoadForm();

        cbProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadImage(cbProfile.getSelectedIndex() + 1);
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    private void LoadForm(){
        lblWelcome.setText("Bem-Vindo, " + customer.getName() + "!");
        edtName.setText(customer.getName());
        edtPsw.setText("");
        fEdtCpf.setText(customer.getCpf());
        edtEmail.setText(customer.getEmail());

        cbProfile.addItem("Perfil 1");
        cbProfile.addItem("Perfil 2");
        cbProfile.addItem("Perfil 3");
        cbProfile.addItem("Perfil 4");
        cbProfile.addItem("Perfil 5");
        cbProfile.addItem("Perfil 6");
        cbProfile.addItem("Perfil 7");
        cbProfile.addItem("Perfil 8");
        cbProfile.addItem("Perfil 9");
        cbProfile.addItem("Perfil 10");
        cbProfile.addItem("Perfil 11");
        if(customer.getProfileId() > 0){
            cbProfile.setSelectedIndex(customer.getProfileId() - 1);
            loadImage(customer.getProfileId());
        }else{
            loadImage(0);
        }
    }

    private void loadImage(int index){
        ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("profile/"+index+".png")));
        Image image = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        lblProfile.setIcon(new ImageIcon(image));
    }

    private void save(){
        int roleId = SessionManager.getLoggedUserRole() == Role.ADMIN ? 0 : 1;

        try{
            if(!edtPsw.getText().isBlank()){
                controller.updateCustomer(SessionManager.getLoggedUserId(), fEdtCpf.getText(), edtEmail.getText(), edtName.getText(),
                        edtPsw.getText(), roleId, cbProfile.getSelectedIndex()+1);
                SessionManager.Login(edtEmail.getText(), edtPsw.getText());
            }else{
                controller.updateCustomer(SessionManager.getLoggedUserId(), fEdtCpf.getText(), edtEmail.getText(), edtName.getText(),
                        customer.getPassword(), roleId, cbProfile.getSelectedIndex()+1);
                SessionManager.Login(edtEmail.getText(), customer.getPassword());
            }
            dispose();
        }catch(CustomerException | AuthException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void initCustomComponents() {
        FormatterUtils.applyCpfMask(fEdtCpf);
    }
}