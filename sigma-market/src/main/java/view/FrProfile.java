package view;

import Auth.SessionManager;
import controller.CustomerController;
import model.entities.Customer;
import model.enums.Role;
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
    private JLabel lblNameValue;
    private JLabel lblPswValue;
    private JLabel lblCpfValue;
    private JLabel lblEmail;
    private JLabel lblEmailValue;
    private JLabel lblProfile;
    private JPanel panMain;
    private JComboBox<String> cbProfile;
    private JButton btnSave;

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
                dispose();
            }
        });
    }

    private void LoadForm(){
        lblWelcome.setText("Bem-Vindo, " + customer.getName() + "!");
        lblNameValue.setText(customer.getName());
        lblPswValue.setText(customer.getPassword());
        lblCpfValue.setText(customer.getCpf());
        lblEmailValue.setText(customer.getEmail());

        cbProfile.addItem("Perfil 1");
        cbProfile.addItem("Perfil 2");
        cbProfile.addItem("Perfil 3");
        cbProfile.addItem("Perfil 4");
        cbProfile.addItem("Perfil 5");
        cbProfile.addItem("Perfil 6");
        cbProfile.addItem("Perfil 7");
        cbProfile.addItem("Perfil 8");
        cbProfile.addItem("Perfil 9");
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
        controller.updateCustomer(SessionManager.getLoggedUserId(), lblCpfValue.getText(), lblEmailValue.getText(), lblNameValue.getText(),
                lblPswValue.getText(), roleId, cbProfile.getSelectedIndex()+1);
    }
}