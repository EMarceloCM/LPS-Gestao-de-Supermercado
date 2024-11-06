package controller.tableModel;

import model.entities.ShoppingCart;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class TMShoppingCart extends AbstractTableModel {
    private List<ShoppingCart> sps;

    private final int COL_PRD_NAME = 0;
    private final int COL_PRD_DESC = 1;
    private final int COL_PRD_PRICE = 2;
    private final int COL_QUANTITY = 3;
    private final int COL_TOTAL_AMOUNT = 4;
    private final int COL_REMOVE = 5;

    public TMShoppingCart(List<ShoppingCart> sps) {
        this.sps = sps;
    }

    @Override
    public int getRowCount() {
        return sps.size();
    }

    @Override
    public int getColumnCount() { return 6; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ShoppingCart sp = new ShoppingCart();

        if(sps.isEmpty()) return sp;
        sp = sps.get(rowIndex);

        return switch (columnIndex) {
            case COL_PRD_NAME -> sp.getProduct().getName();
            case COL_PRD_DESC -> sp.getProduct().getDescription();
            case COL_PRD_PRICE -> sp.getProduct().getPrice();
            case COL_QUANTITY -> sp.getQuantity();
            case COL_TOTAL_AMOUNT -> sp.getTotalAmount();
            case COL_REMOVE -> {
                JLabel label = new JLabel();
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/icons/delete-bin-line.png")));
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(20, 20,  Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaledImg));
                label.setHorizontalAlignment(JLabel.CENTER);
                yield label;
            }
            default -> sp;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_PRD_NAME -> "Produto";
            case COL_PRD_DESC -> "Descrição";
            case COL_PRD_PRICE -> "Preço";
            case COL_QUANTITY -> "Quantidade";
            case COL_TOTAL_AMOUNT -> "Valor total";
            case COL_REMOVE -> "Remover";
            default -> "";
        };
    }
}