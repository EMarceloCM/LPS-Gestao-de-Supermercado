package view;

import Auth.exceptions.AuthException;
import controller.CustomerController;
import model.exceptions.CustomerException;
import view.utils.FormatterUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FrLogin extends JDialog {
    private JPanel panMain;
    private JPanel panTop;
    private JPanel panForm;
    private JLabel lblEmail;
    private JTextField edtEmail;
    private JLabel lblPassword;
    private JPasswordField pswUserPassword;
    private JButton btnLoginSignin;
    private JLabel lblTitle;
    private JLabel lblSigninOrLogin;
    private JLabel lblNome;
    private JTextField edtNome;
    private JFormattedTextField fEdtCpf;
    private JLabel lblCpf;

    private boolean isLogin;
    private CustomerController controller;

    public FrLogin(Frame parent, boolean modal) {
        super(parent, modal);
        setContentPane(panMain);
        setTitle("Login");
        setSize(380, 280);

        controller = new CustomerController();
        isLogin = false;
        swapLoginLogout();

        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblSigninOrLogin.setCursor(hand);
        FormatterUtils.applyCpfMask(fEdtCpf);

        lblSigninOrLogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                swapLoginLogout();
            }
        });
        btnLoginSignin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (isLogin) {
                        Auth.SessionManager.Login(edtEmail.getText(), new String(pswUserPassword.getPassword()));
                        dispose();
                    } else {
                        controller.createCustomer(fEdtCpf.getText(), edtEmail.getText(), edtNome.getText(), new String(pswUserPassword.getPassword()), 1);
                        Auth.SessionManager.Login(edtEmail.getText(), new String(pswUserPassword.getPassword()));
                        dispose();
                    }
                } catch (AuthException | CustomerException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // swap the state of isLogin
    private void swapLoginLogout() {
        isLogin = !isLogin;

        lblCpf.setVisible(!isLogin);
        fEdtCpf.setVisible(!isLogin);
        lblNome.setVisible(!isLogin);
        edtNome.setVisible(!isLogin);

        if (isLogin) {
            lblTitle.setText("Fazer Login");
            btnLoginSignin.setText("Entrar");
            btnLoginSignin.setIcon(new ImageIcon(getClass().getResource("/icons/login.png")));
            lblSigninOrLogin.setText("Cadastrar-se");
        } else {
            lblTitle.setText("Criar usuário");
            btnLoginSignin.setText("Cadastrar-se");
            btnLoginSignin.setIcon(new ImageIcon(getClass().getResource("/icons/newuser.png")));
            lblSigninOrLogin.setText("Realizar Login");
        }
    }
}
