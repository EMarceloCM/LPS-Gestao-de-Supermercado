package controller.tableModel;

import model.entities.Feedback;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMFeedback extends AbstractTableModel {
    private List<Feedback> list;

    private final int COL_CUSTOMER_ID = 0;
    private final int COL_ORDER_ID = 1;
    private final int COL_DATE = 2;
    private final int COL_REVIEW = 3;
    private final int COL_STARS = 4;

    public TMFeedback(List<Feedback> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Feedback f = new Feedback();

        if (list.isEmpty())
            return f;

        f = list.get(rowIndex);

        return switch (columnIndex) {
            case COL_CUSTOMER_ID -> f.getCustomer().getId();
            case COL_ORDER_ID -> f.getOrder().getId();
            case COL_DATE -> f.getDate();
            case COL_REVIEW -> f.getReview();
            case COL_STARS -> f.getStars();
            default -> f;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_CUSTOMER_ID -> "Id do Cliente";
            case COL_ORDER_ID -> "Id do Pedido";
            case COL_DATE -> "Data";
            case COL_REVIEW -> "Avaliação";
            case COL_STARS -> "Estrelas";
            default -> "";
        };
    }
}