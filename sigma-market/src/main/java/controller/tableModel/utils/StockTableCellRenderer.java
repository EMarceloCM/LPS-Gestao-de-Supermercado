package controller.tableModel.utils;

import controller.tableModel.TMProductBuy;
import model.entities.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class StockTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                   boolean hasFocus, int row, int column) {
        Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        TMProductBuy model = (TMProductBuy) table.getModel();
        Product product = model.getProductAt(row);

        if (product.getStock() == 0) {
            cell.setBackground(new Color(255, 71, 71));
            cell.setForeground(Color.WHITE);
        } else {
            cell.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            cell.setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
        }

        return cell;
    }
}