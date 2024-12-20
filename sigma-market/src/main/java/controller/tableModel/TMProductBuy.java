package controller.tableModel;

import model.entities.Product;
import model.entities.Promotion;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class TMProductBuy extends AbstractTableModel {
    private List<Product> list;

    private final int COL_NAME = 0;
    private final int COL_DESC = 1;
    private final int COL_PRICE = 2;
    private final int COL_DETAILS = 3;

    public TMProductBuy(List<Product> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product o = new Product();

        if (list.isEmpty())
            return o;

        o = list.get(rowIndex);

        Promotion p = null;
        boolean hasPromotion = o.getPromotions() != null && !o.getPromotions().isEmpty();
        if(hasPromotion){
            for(int i = 0; i < o.getPromotions().size(); i++) {
                if(o.getPromotions().get(i).getActive() == 1){
                    p = o.getPromotions().get(i);
                    break;
                }
            }
        }
        hasPromotion = p != null;


        return switch (columnIndex) {
            case COL_NAME -> o.getName();
            case COL_DESC -> o.getDescription();
            case COL_PRICE -> (hasPromotion ? "de " : "") + "R$ " +  String.format("%.2f", o.getPrice()) + (hasPromotion ? " por R$ " + String.format("%.2f", p.getFinalPrice()) : "");
            case COL_DETAILS -> {
                JLabel label = new JLabel();
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/eye-line.png")));
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImg));
                label.setHorizontalAlignment(JLabel.CENTER);
                yield label;
            }
            default -> o;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_NAME -> "Nome";
            case COL_DESC -> "Descrição";
            case COL_PRICE -> "Preço";
            case COL_DETAILS -> "Ver";
            default -> "";
        };
    }

    public Product getProductAt(int rowIndex) {
        return list.get(rowIndex);
    }
}