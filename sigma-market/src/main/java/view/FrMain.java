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

public class FrMain extends JFrame {
    private JLabel lblNome;
    private JPanel panMain;
    private JPanel panSearch;
    private JTextField edtSearch;
    private JLabel lblSearchImg;
    private JLabel lblSearch;
    private JLabel lblReference;
    private JButton btnLoginOrLogout;
    private JButton btnCart;
    private JTable grdProducts;
    private JPanel panLeft;
    private JPanel panMenuTitle;
    private JLabel lblMenu;
    private JPanel panMenuItems;
    private JPanel panMenuAdmin;
    private JButton btnUsers;
    private JButton btnSupplier;
    private JButton btnProduct;
    private JButton btnPromotion;
    private JButton btnRelOrders;
    private JButton btnStock;
    private JButton btnAddress;
    private JButton btnOrders;
    private JButton btnRelFeedbacks;
    private JButton btnProfile;
    private JPanel panBot;
    private ProductController controller;
    private PromotionController promotionController;
    private FrProductDetail dlgProductDetail;

    public FrMain() {
        setContentPane(panMain);
        setTitle("Supermercado Sigma");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1280, 960);
        setLocationRelativeTo(null);
        setVisible(true);
        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/image/logo3.jpg"));
        setIconImage(icon);

        controller = new ProductController();
        promotionController = new PromotionController();
        dlgProductDetail = null;

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
                FrSupplier dlg = new FrSupplier(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
            }
        });
        btnUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrCustomer dlg = new FrCustomer(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
            }
        });
        btnProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrProduct dlg = new FrProduct(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnPromotion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrPromotion dlg = new FrPromotion(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnAddress.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrAddress dlg = new FrAddress(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnLoginOrLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Objects.equals(btnLoginOrLogout.getText(), "Login")) {
                    FrLogin dlg = new FrLogin(FrMain.this, true);
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
                    dlg.setLocationRelativeTo(FrMain.this);
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
                    FrShoppingCart dlg = new FrShoppingCart(FrMain.this, true);
                    dlg.setLocationRelativeTo(FrMain.this);
                    dlg.setVisible(true);
                }else{
                    JOptionPane.showMessageDialog(null, "Realize login!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrProfile dlg = new FrProfile(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrOrder dlg = new FrOrder(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnRelFeedbacks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrFeedback dlg = new FrFeedback(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        lblSearchImg.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                controller.filterBuyTable(grdProducts, edtSearch.getText());
                configureGrdAfterTModel();
            }
        });
        btnRelOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrSellingReport dlg = new FrSellingReport(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
        btnStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrViewStock dlg = new FrViewStock(FrMain.this, true);
                dlg.setLocationRelativeTo(FrMain.this);
                dlg.setVisible(true);
                controller.refreshBuyTable(grdProducts);
                configureGrdAfterTModel();
            }
        });
    }

    private void initCustomComponents() {
        // set clickable buttons
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        lblReference.setCursor(hand);
        btnCart.setCursor(hand);
        btnLoginOrLogout.setCursor(hand);
        lblSearchImg.setCursor(hand);
        grdProducts.setCursor(hand);

        // set table layout
        grdProducts.setDefaultEditor(Object.class, null);
        grdProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        grdProducts.getTableHeader().setReorderingAllowed(false);
        grdProducts.setRowHeight(30);
    }

    private void configureGrdAfterTModel(){
        grdProducts.getColumnModel().getColumn(3).setCellRenderer(new IconLabelRenderer());
        grdProducts.setDefaultRenderer(Object.class, new StockTableCellRenderer());

        for (MouseListener listener : grdProducts.getMouseListeners()) {
            grdProducts.removeMouseListener(listener);
        }

        grdProducts.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = grdProducts.rowAtPoint(e.getPoint());

                Product aux = (Product) grdProducts.getModel().getValueAt(row, -1);
                dlgProductDetail = new FrProductDetail(FrMain.this, true, aux, promotionController.findActiveByProductId(aux.getId()));
                dlgProductDetail.setLocationRelativeTo(FrMain.this);
                dlgProductDetail.setVisible(true);
            }
        });
        grdProducts.getColumnModel().getColumn(0).setMinWidth(60);
        grdProducts.getColumnModel().getColumn(0).setMaxWidth(405);
        grdProducts.getColumnModel().getColumn(0).setPreferredWidth(270);
        grdProducts.getColumnModel().getColumn(1).setMinWidth(60);
        grdProducts.getColumnModel().getColumn(1).setMaxWidth(725);
        grdProducts.getColumnModel().getColumn(1).setPreferredWidth(550);
        grdProducts.getColumnModel().getColumn(2).setMinWidth(230);
        grdProducts.getColumnModel().getColumn(2).setMaxWidth(230);
        grdProducts.getColumnModel().getColumn(2).setPreferredWidth(230);
        grdProducts.getColumnModel().getColumn(3).setMinWidth(30);
        grdProducts.getColumnModel().getColumn(3).setMaxWidth(30);
        grdProducts.getColumnModel().getColumn(3).setPreferredWidth(30);


    }

    private void changeViewBasedOnRole() {
        if (Auth.SessionManager.isLoggedIn()) {
            if (Auth.SessionManager.getLoggedUserRole() == Role.ADMIN) {
                panMenuAdmin.setVisible(true);
            } else if (Auth.SessionManager.getLoggedUserRole() == Role.CUSTOMER) {
                panMenuAdmin.setVisible(false);
            }
            panLeft.setVisible(true);
            btnCart.setVisible(true);
            btnLoginOrLogout.setText("Logout");
            btnLoginOrLogout.setIcon(new ImageIcon(getClass().getResource("/icons/logout.png")));
        } else {
            panLeft.setVisible(false);
            btnCart.setVisible(false);
            btnLoginOrLogout.setText("Login");
            btnLoginOrLogout.setIcon(new ImageIcon(getClass().getResource("/icons/login.png")));
        }
    }
}
