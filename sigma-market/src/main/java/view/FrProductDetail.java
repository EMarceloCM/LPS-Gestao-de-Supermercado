package view;

import Auth.SessionManager;
import Auth.exceptions.AuthException;
import controller.ShoppingCartController;
import model.entities.Product;
import model.entities.Promotion;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
        setSize(880, 490);
        setTitle("Detalhes do Produto");
        LoadForm();

        btnAddToCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(product.getStock() == 0) JOptionPane.showMessageDialog(null, "Item indisponível no momento!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                else if((Integer) spnQuantityValue.getValue() == 0) JOptionPane.showMessageDialog(null, "Quantidade do item não pode ser zero!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                else if((Integer) spnQuantityValue.getValue() > product.getStock()) JOptionPane.showMessageDialog(null, "Quantidade do item está acima do estoque disponível!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                else AddProductToCart();
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
            lblPriceValue.setText("De R$ "+ product.getPrice() +" Por R$ "+ String.format("%.2f", promotion.getFinalPrice()));
            lblDiscountValue.setText("R$ "+ String.format("%.2f", (product.getPrice() - promotion.getFinalPrice())));
        }

        try {
            URI imageUri = new URI(product.getImgUrl());
            URL imageUrl = imageUri.toURL();
            ImageIcon icon = new ImageIcon(imageUrl);
            Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);

            lblImage.setIcon(new ImageIcon(scaledImage));
            lblImage.setHorizontalAlignment(JLabel.CENTER);
        } catch (URISyntaxException | MalformedURLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void AddProductToCart(){
        float totalAmount = promotion == null ?
                product.getPrice() * (Integer) spnQuantityValue.getValue() :
                promotion.getFinalPrice() * (Integer) spnQuantityValue.getValue();

        try{
            cartController.createShoppingCart(product, SessionManager.getLoggedUser(), (Integer) spnQuantityValue.getValue(), totalAmount);
            dispose();
        } catch (AuthException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}