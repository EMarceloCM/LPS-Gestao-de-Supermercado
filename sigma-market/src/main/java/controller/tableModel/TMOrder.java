package controller.tableModel;

import model.entities.Order;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class TMOrder extends AbstractTableModel {
    private List<Order> lst;

    private final int COL_ID = 0;
    private final int COL_CUSTOMER_ID = 1;
    private final int COL_ADDRESS_ID = 2;
    private final int COL_FEEDBACK_ID = 3;
    private final int COL_DATE = 4;
    private final int COL_TOTAL_AMOUNT = 5;
    private final int COL_ORDER_STATUS = 6;
    private final int COL_PAYMENT_STATUS = 7;
    private final int COL_PAYMENT_TYPE = 8;
    private final int COL_DETAILS = 9;

    public TMOrder(List<Order> lst) {
        this.lst = lst;
    }

    @Override
    public int getRowCount() {
        return lst.size();
    }

    @Override
    public int getColumnCount() {
        return 10;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order o = new Order();

        if(lst.isEmpty()) return o;
        o = lst.get(rowIndex);

        return switch (columnIndex) {
            case COL_ID -> o.getId();
            case COL_CUSTOMER_ID -> o.getCustomer().getId();
            case COL_ADDRESS_ID -> o.getAddress().getId();
            case COL_FEEDBACK_ID -> o.getFeedback() != null ? o.getFeedback().getId() : "null";
            case COL_DATE -> o.getDate();
            case COL_TOTAL_AMOUNT -> o.getTotalAmount();
            case COL_ORDER_STATUS -> o.getOrderStatus().toString();
            case COL_PAYMENT_STATUS -> o.getPaymentStatus().toString();
            case COL_PAYMENT_TYPE -> o.getPaymentType().toString();
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
            case COL_ID -> "Id";
            case COL_CUSTOMER_ID -> "Id do Cliente";
            case COL_ADDRESS_ID -> "Id do Endereço";
            case COL_FEEDBACK_ID -> "Id do Feedback";
            case COL_DATE -> "Data de Compra";
            case COL_TOTAL_AMOUNT -> "Valor Total";
            case COL_ORDER_STATUS -> "Status do Pedido";
            case COL_PAYMENT_STATUS -> "Status do Pagamento";
            case COL_PAYMENT_TYPE -> "Tipo de Pagamento";
            case COL_DETAILS -> "Ver";
            default -> "";
        };
    }
}