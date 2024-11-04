package controller.tableModel;

import model.entities.Order;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class TMCustomerOrder extends AbstractTableModel {
    private List<Order> lst;

    private final int COL_ID = 0;
    private final int COL_DATE = 1;
    private final int COL_ADDRESS = 2;
    private final int COL_TOTAL_AMOUNT = 3;
    private final int COL_FEEDBACK_REVIEW = 4;
    private final int COL_ORDER_STATUS = 5;
    private final int COL_PAYMENT_STATUS = 6;
    private final int COL_PAYMENT_TYPE = 7;
    private final int COL_DETAILS = 8;

    public TMCustomerOrder(List<Order> lst) {
        this.lst = lst;
    }

    @Override
    public int getRowCount() {
        return lst.size();
    }

    @Override
    public int getColumnCount() { return 9; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Order o = new Order();

        if(lst.isEmpty()) return o;
        o = lst.get(rowIndex);

        return switch (columnIndex) {
            case COL_ID -> o.getId();
            case COL_DATE -> o.getDate();
            case COL_ADDRESS -> o.getAddress().getStreet() + o.getAddress().getNumber();
            case COL_TOTAL_AMOUNT -> o.getTotalAmount();
            case COL_FEEDBACK_REVIEW -> o.getFeedback() == null ? "" : o.getFeedback().getReview();
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
            case COL_DATE -> "Data da Compra";
            case COL_ADDRESS -> "Endereço";
            case COL_TOTAL_AMOUNT -> "Valor Total";
            case COL_FEEDBACK_REVIEW -> "Feedback";
            case COL_ORDER_STATUS -> "Status";
            case COL_PAYMENT_STATUS -> "Status do Pagamento";
            case COL_PAYMENT_TYPE -> "Tipo de Pagamento";
            case COL_DETAILS -> "Ver";
            default -> "";
        };
    }

    public Order getOrderAt(int rowIndex) {
        return lst.get(rowIndex);
    }
}