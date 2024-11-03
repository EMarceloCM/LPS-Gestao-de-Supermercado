package view;

import Auth.SessionManager;
import controller.OrderController;
import controller.ShoppingCartController;
import model.entities.ShoppingCart;
import javax.swing.*;
import java.awt.*;
import java.util.List;

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

    private ShoppingCartController shoppingCartController;
    private OrderController orderController;
    private List<ShoppingCart> shoppingCartList;

    public FrShoppingCart(Frame parent, boolean modal){
        super(parent, modal);
        shoppingCartController = new ShoppingCartController();
        orderController = new OrderController();
        setContentPane(panMain);
        setTitle("Carrinho de Compras");
        setSize(840, 570);
        setMinimumSize(new Dimension(1200, 620));

        shoppingCartController.refreshTable(grdItemOrder);
        initCustomComponents();
        configureGrdAfterTModel();
    }

    private void initCustomComponents() {
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        btnCreateOrder.setCursor(hand);
        shoppingCartList = shoppingCartController.findByCustomer(SessionManager.getLoggedUserId());

        if(shoppingCartList != null && !shoppingCartList.isEmpty()) initLabels();
        if(shoppingCartList == null) JOptionPane.showMessageDialog(null, "Carrinho vazio!", "Aviso", JOptionPane.INFORMATION_MESSAGE);

        cbAddressValue.addItem("chamar AddressController");
        cbPaymentTypeValue.addItem("Dinheiro");
        cbPaymentTypeValue.addItem("PIX");
        cbPaymentTypeValue.addItem("Bitcoin");
    }

    private void initLabels(){
        float aux = 0;
        for (ShoppingCart shoppingCart : shoppingCartList) {
            aux += shoppingCart.getTotalAmount();
        }
        lblTotalValue.setText(String.valueOf(aux));
    }

    private void configureGrdAfterTModel(){
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
    }
}