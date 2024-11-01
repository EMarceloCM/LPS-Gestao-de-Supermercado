package view;

import Auth.SessionManager;
import controller.ShoppingCartController;
import model.entities.Product;
import model.entities.Promotion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrProductDetail extends JDialog{
    private JPanel panTop;
    private JLabel lblName;
    private JButton btnAddToCart;
    private JPanel panForm;
    private JPanel panFormCore;
    private JLabel lblDescription;
    private JLabel lblPrice;
    private JLabel lblStock;
    private JSpinner spnQuantityValue;
    private JLabel lblQuantity;
    private JLabel lblImage;
    private JLabel lblDescriptionValue;
    private JLabel lblPriceValue;
    private JLabel lblStockValue;
    private JLabel lblDiscount;
    private JLabel lblDiscountValue;
    private JPanel panMain;

    private ShoppingCartController cartController;
    private Product product = null;
    private Promotion promotion = null;

    public FrProductDetail(Frame parent, boolean modal, Product product, Promotion promotion) {
        super(parent, modal);
        this.product = product;
        this.promotion = promotion;
        cartController = new ShoppingCartController();
        setContentPane(panMain);
        setTitle("Detalhes do Produto");
        LoadForm();

        btnAddToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((Integer) spnQuantityValue.getValue() != 0) AddProductToCart();
                else JOptionPane.showMessageDialog(null, "Quantidade do item não pode ser zero!", "Aviso.", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    private void LoadForm(){
        lblName.setText(product.getName());
        lblDescriptionValue.setText(product.getDescription());
        lblStockValue.setText(product.getStock() +" unidades");

        if(promotion == null){
            lblPriceValue.setText("R$ "+ product.getPrice());
            lblDiscount.setVisible(false);
            lblDiscountValue.setVisible(false);
        } else {
            lblPriceValue.setText("De R$ "+ product.getPrice() +" Por R$ "+promotion.getFinalPrice());
            lblDiscountValue.setText("R$ "+ (product.getPrice() - promotion.getFinalPrice()));
        }

        // TODO lblImage.setIcon(product.getImgUrl());
    }

    private void AddProductToCart(){
        float totalAmount = promotion == null ?
                product.getPrice() * (Integer) spnQuantityValue.getValue() :
                promotion.getFinalPrice() * (Integer) spnQuantityValue.getValue();

        cartController.createShoppingCart(product, SessionManager.getLoggedUser(), (Integer) spnQuantityValue.getValue(), totalAmount);
        dispose();
    }
}