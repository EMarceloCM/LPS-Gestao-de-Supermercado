package controller.tableModel.utils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class IconLabelRenderer extends JLabel implements TableCellRenderer {

    public IconLabelRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setIcon(new ImageIcon("resources/icons/eye-line.png"));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JLabel) {
            JLabel label = (JLabel) value;
            label.setOpaque(true);
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
                label.setForeground(table.getSelectionForeground());
            } else {
                label.setBackground(table.getBackground());
                label.setForeground(table.getForeground());
            }
            return label;
        }
        return this;
    }
}