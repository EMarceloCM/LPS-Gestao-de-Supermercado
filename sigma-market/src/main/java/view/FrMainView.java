package view;

import Auth.SessionManager;
import controller.ProductController;
import controller.PromotionController;
import controller.tableModel.utils.IconLabelRenderer;
import controller.tableModel.utils.StockTableCellRenderer;
import model.entities.Product;
import model.enums.Role;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class FrMainView extends JFrame {
    private JPanel panMain;
    private JTable grdProducts;
    private JLabel lblNome;
    private JLabel lblMenu;
    private JPanel panTop;
    private JPanel panLeft;
    private JPanel panMenuTitle;
    private JButton btnUsers;
    private JPanel panMenuItems;
    private JButton btnSupplier;
    private JButton btnProduct;
    private JButton btnEndereco;
    private JButton btnRelOrders;
    private JButton btnRelFeedbacks;
    private JButton btnOrders;
    private JButton btnLoginOrLogout;
    private JButton btnCart;
    private JButton btnPromotion;
    private JPanel panTopLeft;
    private JPanel panReference;
    private JLabel lblReference;
    private JPanel panMenuAdmin;
    private JButton btnProfile;
    private JButton btnStock;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearch;
    private JLabel lblSearchImg;

    private ProductController controller;
    private PromotionController promotionController;

    public FrMainView() {
        // window info
        setContentPane(panMain);
        setTitle("Supermercado Sigma");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 680);
        setLocationRelativeTo(null);
        setVisible(true);

        controller = new ProductController();
        promotionController = new PromotionController();

        initCustomComponents();
        changeViewBasedOnRole();

        controller.refreshBuyTable(grdProducts);
        configureGrdAfterTModel();

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
        btnSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrSupplier dlg = new FrSupplier(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        btnUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrCustomer dlg = new FrCustomer(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        btnProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrProduct dlg = new FrProduct(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnPromotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrPromotion dlg = new FrPromotion(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        btnEndereco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrAddress dlg = new FrAddress(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        btnLoginOrLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(btnLoginOrLogout.getText(), "Login")) {
                    FrLogin dlg = new FrLogin(FrMainView.this, true);
                    dlg.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            changeViewBasedOnRole();
                        }

                        @Override
                        public void windowClosing(WindowEvent e) {
                            changeViewBasedOnRole();
                        }
                    });
                    dlg.setLocationRelativeTo(FrMainView.this);
                    dlg.setVisible(true);
                } else {
                    Auth.SessionManager.Logout();
                    changeViewBasedOnRole();
                }
            }
        });
        btnCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(SessionManager.isLoggedIn()) {
                    FrShoppingCart dlg = new FrShoppingCart(FrMainView.this, true);
                    dlg.setLocationRelativeTo(FrMainView.this);
                    dlg.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Realize login!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        btnProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrProfile dlg = new FrProfile(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        btnOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrOrder dlg = new FrOrder(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        btnRelFeedbacks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrFeedback dlg = new FrFeedback(FrMainView.this, true);
                dlg.setLocationRelativeTo(FrMainView.this);
                dlg.setVisible(true);
            }
        });
        lblSearchImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.filterBuyTable(grdProducts, edtSearch.getText());
            }
        });
    }

    private void initCustomComponents() {
        // set clickable buttons
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblReference.setCursor(hand);
        btnCart.setCursor(hand);
        btnLoginOrLogout.setCursor(hand);

        // set table layout
        grdProducts.setDefaultEditor(Object.class, null);
        grdProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grdProducts.getTableHeader().setReorderingAllowed(false);
    }

    private void configureGrdAfterTModel(){
        grdProducts.getColumnModel().getColumn(4).setCellRenderer(new IconLabelRenderer());
        grdProducts.setDefaultRenderer(Object.class, new StockTableCellRenderer());
        grdProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = grdProducts.rowAtPoint(e.getPoint());
                int column = grdProducts.columnAtPoint(e.getPoint());

                if (column == 4 && row >= 0) {
                    Product aux = controller.findProductById(row+1);
                    FrProductDetail dlg = new FrProductDetail(FrMainView.this,true, aux, promotionController.findActiveByProductId(aux.getId()));
                    dlg.setLocationRelativeTo(FrMainView.this);
                    dlg.setVisible(true);
                }
            }
        });
        grdProducts.getColumnModel().getColumn(0).setMinWidth(60);
        grdProducts.getColumnModel().getColumn(0).setMaxWidth(405);
        grdProducts.getColumnModel().getColumn(0).setPreferredWidth(270);
        grdProducts.getColumnModel().getColumn(1).setMinWidth(60);
        grdProducts.getColumnModel().getColumn(1).setMaxWidth(725);
        grdProducts.getColumnModel().getColumn(1).setPreferredWidth(550);
        grdProducts.getColumnModel().getColumn(2).setMinWidth(60);
        grdProducts.getColumnModel().getColumn(2).setMaxWidth(60);
        grdProducts.getColumnModel().getColumn(2).setPreferredWidth(60);
        grdProducts.getColumnModel().getColumn(3).setMinWidth(80);
        grdProducts.getColumnModel().getColumn(3).setMaxWidth(180);
        grdProducts.getColumnModel().getColumn(3).setPreferredWidth(180);
        grdProducts.getColumnModel().getColumn(4).setMinWidth(30);
        grdProducts.getColumnModel().getColumn(4).setMaxWidth(30);
        grdProducts.getColumnModel().getColumn(4).setPreferredWidth(30);
    }

    private void changeViewBasedOnRole() {
        if (Auth.SessionManager.isLoggedIn()) {
            if (Auth.SessionManager.getLoggedUserRole() == Role.ADMIN) {
                panMenuAdmin.setVisible(true);
            } else if (Auth.SessionManager.getLoggedUserRole() == Role.CUSTOMER) {
                panMenuAdmin.setVisible(false);
            }
            panMenuItems.setVisible(true);
            lblMenu.setText("Menu");
            btnCart.setVisible(true);
            btnLoginOrLogout.setText("Logout");
            btnLoginOrLogout.setIcon(new ImageIcon(getClass().getResource("/icons/logout.png")));
        } else {
            lblMenu.setText("Realize login!");
            btnCart.setVisible(false);
            panMenuItems.setVisible(false);
            btnLoginOrLogout.setText("Login");
            btnLoginOrLogout.setIcon(new ImageIcon(getClass().getResource("/icons/login.png")));
        }
    }
}