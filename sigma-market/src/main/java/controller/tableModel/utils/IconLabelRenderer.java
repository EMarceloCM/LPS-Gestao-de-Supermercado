package controller.tableModel.utils;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class IconLabelRenderer extends JLabel implements TableCellRenderer {

    public IconLabelRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setIcon(new ImageIcon("resources/icons/eye-line.png"));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "FOI CLICADO");
            }
        });
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