package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

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
        initializeGrd();
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

    private void initializeGrd() {
        DefaultTableModel t = (DefaultTableModel) grdProducts.getModel();
        t.setColumnCount(0);

        t.addColumn("Imagem");
        t.addColumn("Nome");
        t.addColumn("Descrição");
        t.addColumn("Preço");
        t.addColumn("Desconto");

        Object[] row1 = {"imagem", "Whey", "Whey protein", 23.5, 0};
//        Object[] row1 = {getImageFromURL("https://www.davisco.in/images/gold-whey-4lbs-a.png"),
//                "Whey", "Whey protein", 23.5, 0};
//        Object[] row2 = {getImageFromURL("https://www.startpage.com/av/proxy-image?piurl=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.vcZZVYaEkhShZ-iz8JK-cwHaHa%26pid%3DApi&sp=1729455438T4b8de8a057f85e7b44ff4883d27946100ef95da1923ea34c684d29bc068f0e70"),
//                "Whey", "Whey protein", 23.5, 0};
//        Object[] row3 = {getImageFromURL("https://auctions.morphyauctions.com/ItemImages/000282/18010902_1_lg.jpeg"),
//                "Whey", "Whey protein", 23.5, 0};

        t.addRow(row1);
//        t.addRow(row2);
//        t.addRow(row3);

        grdProducts.setRowHeight(250);
        grdProducts.getColumnModel().getColumn(0).setPreferredWidth(250);

//        grdProducts.getColumnModel().getColumn(0).setCellRenderer(new ImageRenderer());
    }

    private ImageIcon getImageFromURL(String urlString) {
        try {
            URL url = new URL(urlString);
            ImageIcon imageIcon = new ImageIcon(url);

            Image img = imageIcon.getImage();
            Image resizedImg = img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);

            return new ImageIcon(resizedImg);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class ImageRenderer extends JLabel implements TableCellRenderer {

        public ImageRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof ImageIcon) {
                setIcon((ImageIcon) value);
            } else {
                // setText("No Image");
            }
            return this;
        }
    }
}