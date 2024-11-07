package view;

import Auth.SessionManager;
import Auth.exceptions.AuthException;
import controller.ShoppingCartController;
import model.entities.Product;
import model.entities.Promotion;
import model.entities.ShoppingCart;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class FrProductDetail extends JDialog {
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
                else if((Integer) spnQuantityValue.getValue() <= 0) JOptionPane.showMessageDialog(null, "Quantidade do item não pode ser zero!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                else if((Integer) spnQuantityValue.getValue() > product.getStock()) JOptionPane.showMessageDialog(null, "Quantidade do item está acima do estoque disponível!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                else AddProductToCart();
            }
        });
    }

    private void LoadForm(){
        lblName.setText(product.getName());
        lblDescriptionValue.setText(product.getDescription());
        lblStockValue.setText(product.getStock() + " unidades");

        if(promotion == null){
            lblPriceValue.setText("R$ "+ product.getPrice());
            lblDiscount.setVisible(false);
            lblDiscountValue.setVisible(false);
        } else {
            lblPriceValue.setText("De R$ "+ product.getPrice() +" Por R$ "+ String.format("%.2f", promotion.getFinalPrice()));
            lblDiscountValue.setText("R$ "+ String.format("%.2f", (product.getPrice() - promotion.getFinalPrice())));
        }

        ImageIcon icon = new ImageIcon(getClass().getResource("/image/produto_generico.png"));
        Image scaledImage = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        lblImage.setIcon(new ImageIcon(scaledImage));
        lblImage.setHorizontalAlignment(JLabel.CENTER);

        SwingUtilities.invokeLater(() -> {
            try {
                URI imageUri = new URI(product.getImgUrl());
                URL imageUrl = imageUri.toURL();
                ImageIcon productIcon = new ImageIcon(imageUrl);
                if (productIcon.getIconWidth() != -1 && productIcon.getIconHeight() != -1) {
                    int originalWidth = productIcon.getIconWidth();
                    int originalHeight = productIcon.getIconHeight();

                    int targetWidth = 250;
                    int targetHeight = 250;

                    double aspectRatio = (double) originalWidth / originalHeight;
                    int newWidth = targetWidth;
                    int newHeight = (int) (targetWidth / aspectRatio);

                    if (newHeight > targetHeight) {
                        newHeight = targetHeight;
                        newWidth = (int) (targetHeight * aspectRatio);
                    }

                    Image scaledProductImage = productIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    lblImage.setIcon(new ImageIcon(scaledProductImage));
                }
            } catch (URISyntaxException | MalformedURLException e) {
                System.err.println("Erro ao carregar a imagem do produto: " + e.getMessage());
            }
        });

    }


    private void AddProductToCart(){
        ShoppingCart existedItemInCart = null;

        List<ShoppingCart> sps = cartController.findByCustomer(SessionManager.getLoggedUserId());
        for(ShoppingCart sp : sps){
            if(sp.getProduct().getId() == product.getId()){
                existedItemInCart = sp;
                if(sp.getQuantity() + (Integer) spnQuantityValue.getValue() > product.getStock()){
                    JOptionPane.showMessageDialog(null, "Quantidade do item (" +(Integer) spnQuantityValue.getValue()+ " + " +sp.getQuantity()+ " já no carrinho) está acima do estoque disponível ("+product.getStock()+")!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
        }

        float totalAmount = promotion == null ?
                product.getPrice() * (Integer) spnQuantityValue.getValue() :
                promotion.getFinalPrice() * (Integer) spnQuantityValue.getValue();

        if(existedItemInCart == null){
            try{
                cartController.createShoppingCart(product, SessionManager.getLoggedUser(), (Integer) spnQuantityValue.getValue(), totalAmount);
                dispose();
            } catch (AuthException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            existedItemInCart.setQuantity(existedItemInCart.getQuantity() + (Integer) spnQuantityValue.getValue());
            existedItemInCart.setTotalAmount(existedItemInCart.getTotalAmount() + totalAmount);

            try{
                cartController.updateShoppingCart(existedItemInCart.getId(), product, SessionManager.getLoggedUser(), existedItemInCart.getQuantity(), existedItemInCart.getTotalAmount());
                dispose();
            } catch (AuthException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}