package controller.tableModel;

import model.entities.Feedback;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMCustomerFeedback extends AbstractTableModel {
    private List<Feedback> list;

    private final int COL_ORDER_ID = 0;
    private final int COL_DATE = 1;
    private final int COL_REVIEW = 2;

    public TMCustomerFeedback(List<Feedback> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Feedback f = new Feedback();

        if (list.isEmpty())
            return f;

        f = list.get(rowIndex);

        return switch (columnIndex) {
            case COL_ORDER_ID -> f.getOrder().getId();
            case COL_DATE -> f.getDate();
            case COL_REVIEW -> f.getReview();
            default -> f;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_ORDER_ID -> "Id do Pedido";
            case COL_DATE -> "Data";
            case COL_REVIEW -> "Avaliação";
            default -> "";
        };
    }
}