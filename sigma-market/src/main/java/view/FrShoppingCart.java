package view;

import Auth.exceptions.AuthException;
import controller.ItemOrderController;
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
    private JTable grd;
    private JPanel panBot;
    private JLabel iconSearchPaymentType;
    private JPanel panGrid;
    private JTextField edtAddress;
    private JLabel iconSearchAdress;
    private JLabel lblPaymentType;
    private JLabel lblPaymentTypeValue;
    private JLabel lblTotal;
    private JLabel lblTotalValue;
    private JPanel panMain;
    private JTable grdItemOrder;

    private ShoppingCartController shoppingCartController;
    private OrderController orderController;
    private List<ShoppingCart> shoppingCartList;

    public FrShoppingCart(Frame parent, boolean modal){
        super(parent, modal);
        shoppingCartController = new ShoppingCartController();
        orderController = new OrderController();
        setContentPane(panMain);
        setTitle("Carrinho de Compras");
        // setSize(900, 500);

        try {
            shoppingCartController.refreshCustomerTable(grdItemOrder);
        }catch(AuthException ex){
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }

        initCustomComponents();
    }

    private void initCustomComponents() {
        Cursor hand = new Cursor(Cursor.HAND_CURSOR);
        iconSearchAdress.setCursor(hand);
        iconSearchPaymentType.setCursor(hand);
        btnCreateOrder.setCursor(hand);

        grd.setDefaultEditor(Object.class, null);
        grd.getTableHeader().setReorderingAllowed(false);

        if(shoppingCartList != null && !shoppingCartList.isEmpty()) initLables();
    }

    private void initLables(){
        float aux = 0;
        for (ShoppingCart shoppingCart : shoppingCartList) {
            aux += shoppingCart.getTotalAmount();
        }
        lblTotalValue.setText(String.valueOf(aux));
    }
}
