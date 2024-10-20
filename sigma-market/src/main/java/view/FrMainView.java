package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class FrMainView extends JFrame {
    private JPanel panMain;
    private JTable grdProducts;
    private JLabel lblNome;
    private JLabel lblMenu;
    private JPanel panTop;
    private JPanel panLeft;
    private JPanel panMenuTitle;
    private JButton btnUsuario;
    private JPanel panMenuItems;
    private JButton btnFornecedor;
    private JButton btnProduct;
    private JButton btnEndereço;
    private JButton btnRelOrders;
    private JButton btnRelFeedbacks;
    private JButton btnOrders;
    private JButton btnLogIn;
    private JButton btnCart;
    private JButton btnPromotion;
    private JPanel panTopLeft;
    private JPanel panReference;
    private JLabel lblReference;

    public FrMainView() {
        // window info
        setContentPane(panMain);
        setTitle("Supermercado Sigma");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setVisible(true);

        // set clickable buttons
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblReference.setCursor(hand);
        btnCart.setCursor(hand);
        btnLogIn.setCursor(hand);

        lblReference.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                try {
                    URI uri = new URI("https://github.com/EMarceloCM/LPS-Gestao-de-Supermercado");
                    Desktop.getDesktop().browse(uri);
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Couldn't open the link");
                }

                super.mouseClicked(e);
            }
        });
        btnFornecedor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrSupplier dlgSupplier = new FrSupplier(FrMainView.this, true);
                dlgSupplier.setLocationRelativeTo(FrMainView.this);
                dlgSupplier.setVisible(true);
            }
        });
    }
}
