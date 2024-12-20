package view;

import Auth.SessionManager;
import controller.*;
import controller.tableModel.utils.IconLabelRenderer;
import model.entities.Address;
import model.entities.Order;
import model.entities.ShoppingCart;
import model.enums.PaymentType;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;

public class FrShoppingCart extends JDialog{
    private JPanel panTop;
    private JLabel lblTitle;
    private JButton btnCreateOrder;
    private JLabel lblAddress;
    private javax.swing.JScrollPane JScrollPane;
    private JPanel panBot;
    private JPanel panGrid;
    private JLabel lblPaymentType;
    private JLabel lblTotal;
    private JLabel lblTotalValue;
    private JPanel panMain;
    private JTable grdItemOrder;
    private JComboBox<String> cbPaymentTypeValue;
    private JComboBox<String> cbAddressValue;
    private JLabel lblPaymentIcon;

    private ShoppingCartController shoppingCartController;
    private AddressController addressController;
    private OrderController orderController;
    private ItemOrderController itemOrderController;
    private List<ShoppingCart> shoppingCartList;
    private List<Address> addresses;

    public FrShoppingCart(Frame parent, boolean modal){
        super(parent, modal);
        shoppingCartController = new ShoppingCartController();
        addressController = new AddressController();
        orderController = new OrderController();
        itemOrderController = new ItemOrderController();
        setContentPane(panMain);
        setTitle("Carrinho de Compras");
        setSize(840, 570);
        setMinimumSize(new Dimension(1200, 620));

        shoppingCartController.refreshTable(grdItemOrder);
        initCustomComponents();
        configureGrdAfterTModel();

        btnCreateOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placesOrder();
            }
        });
        cbPaymentTypeValue.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cbPaymentTypeValue.getSelectedIndex() == 0)
                    lblPaymentIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/money-dollar-circle-line.png"))));
                else if(cbPaymentTypeValue.getSelectedIndex() == 1)
                    lblPaymentIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/pix-line.png"))));
                else
                    lblPaymentIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/btc-line.png"))));
            }
        });
    }

    private void initCustomComponents() {
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        btnCreateOrder.setCursor(hand);
        shoppingCartList = shoppingCartController.findByCustomer(SessionManager.getLoggedUserId());

        if(shoppingCartList != null && !shoppingCartList.isEmpty()) initLabels();
        if(shoppingCartList == null) JOptionPane.showMessageDialog(null, "Carrinho vazio!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        cbPaymentTypeValue.addItem("Dinheiro");
        cbPaymentTypeValue.addItem("PIX");
        cbPaymentTypeValue.addItem("Bitcoin");
        lblPaymentIcon.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/money-dollar-circle-line.png"))));

        addresses = addressController.findByCustomer(SessionManager.getLoggedUserId());
        if(addresses != null && !addresses.isEmpty()){
            for(Address address : addresses){
                cbAddressValue.addItem(address.getStreet() + ", " + address.getNumber() + ".");
            }
        }
    }

    private void initLabels(){
        float aux = 0;
        for (ShoppingCart shoppingCart : shoppingCartList) {
            aux += shoppingCart.getTotalAmount();
        }
        lblTotalValue.setText("R$ " + String.format("%.2f", aux));
    }

    private void configureGrdAfterTModel(){
        grdItemOrder.getColumnModel().getColumn(5).setCellRenderer(new IconLabelRenderer());
        grdItemOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = grdItemOrder.rowAtPoint(e.getPoint());
                int column = grdItemOrder.columnAtPoint(e.getPoint());

                if (column == 5 && row >= 0) {
                    ShoppingCart aux = (ShoppingCart) grdItemOrder.getModel().getValueAt(row, -1);
                    deleteItem(aux);
                }
            }
        });
        grdItemOrder.getColumnModel().getColumn(0).setMaxWidth(405);
        grdItemOrder.getColumnModel().getColumn(0).setPreferredWidth(350);
        grdItemOrder.getColumnModel().getColumn(1).setMinWidth(120);
        grdItemOrder.getColumnModel().getColumn(1).setMaxWidth(725);
        grdItemOrder.getColumnModel().getColumn(1).setPreferredWidth(550);
        grdItemOrder.getColumnModel().getColumn(2).setMinWidth(40);
        grdItemOrder.getColumnModel().getColumn(2).setMaxWidth(40);
        grdItemOrder.getColumnModel().getColumn(2).setPreferredWidth(40);
        grdItemOrder.getColumnModel().getColumn(3).setMinWidth(80);
        grdItemOrder.getColumnModel().getColumn(3).setMaxWidth(110);
        grdItemOrder.getColumnModel().getColumn(3).setPreferredWidth(110);
        grdItemOrder.getColumnModel().getColumn(4).setMinWidth(70);
        grdItemOrder.getColumnModel().getColumn(4).setMaxWidth(70);
        grdItemOrder.getColumnModel().getColumn(4).setPreferredWidth(70);
        grdItemOrder.getColumnModel().getColumn(5).setMinWidth(65);
        grdItemOrder.getColumnModel().getColumn(5).setMaxWidth(65);
        grdItemOrder.getColumnModel().getColumn(5).setPreferredWidth(65);
    }

    private void placesOrder(){
        if(addresses == null || addresses.isEmpty()){
            JOptionPane.showMessageDialog(null, "Cadastre um endereço antes de continuar!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if(shoppingCartList == null || shoppingCartList.isEmpty()){
            JOptionPane.showMessageDialog(null, "Adicione itens ao carrinho!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Address selectedAddress = addresses.get(cbAddressValue.getSelectedIndex());
        PaymentType selectedPType = cbPaymentTypeValue.getSelectedIndex() == 0 ? PaymentType.MONEY :
                (cbPaymentTypeValue.getSelectedIndex() == 1 ? PaymentType.PIX : PaymentType.BITCOIN);

        Order o = orderController.createOrderAndGet(lblTotalValue.getText().replace("R$ ", "").replace(",", "."), selectedPType.toString(), SessionManager.getLoggedUser(), selectedAddress);

        for(ShoppingCart sp : shoppingCartList){
            float ioDiscount = new PromotionController().findActiveByProductId(sp.getProduct().getId()) == null ? 0 : (sp.getProduct().getPrice() - new PromotionController().findActiveByProductId(sp.getProduct().getId()).getFinalPrice()) * sp.getQuantity();
            itemOrderController.createItemOrder(sp.getProduct(), o, sp.getQuantity(), sp.getTotalAmount(), ioDiscount);
            shoppingCartController.deleteShoppingCart(sp.getId());
        }

        FrViewThanks dlg = new FrViewThanks(FrShoppingCart.this, true);
        dlg.setLocationRelativeTo(FrShoppingCart.this);
        dlg.setVisible(true);
        dispose();
    }

    private void deleteItem(ShoppingCart sp){
        shoppingCartController.deleteShoppingCart(sp.getId());
        shoppingCartList = shoppingCartController.findByCustomer(SessionManager.getLoggedUserId());
        shoppingCartController.refreshTable(grdItemOrder);

        initLabels();
        configureGrdAfterTModel();
    }
}