package view;

import controller.ProductController;

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
    private JButton btnEndereco;
    private JButton btnRelOrders;
    private JButton btnRelFeedbacks;
    private JButton btnOrders;
    private JButton btnLogIn;
    private JButton btnCart;
    private JButton btnPromotion;
    private JPanel panTopLeft;
    private JPanel panReference;
    private JLabel lblReference;

    private ProductController productController;

    public FrMainView() {
        // window info
        setContentPane(panMain);
        setTitle("Supermercado Sigma");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setVisible(true);

        productController = new ProductController();

        initCustomComponents();

        productController.refreshTable(grdProducts);

        // TODO campos de pesquisa de itens

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
                FrSupplier dlg = new FrSupplier(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        btnUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrCustomer dlg = new FrCustomer(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
    }

    private void initCustomComponents() {
        // set clickable buttons
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblReference.setCursor(hand);
        btnCart.setCursor(hand);
        btnLogIn.setCursor(hand);

        // set table layout
        grdProducts.setDefaultEditor(Object.class, null);
        grdProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grdProducts.getTableHeader().setReorderingAllowed(false);
    }
}