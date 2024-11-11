package controller.tableModel;

import model.entities.Feedback;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMCustomerFeedback extends AbstractTableModel {
    private List<Feedback> list;

    private final int COL_ORDER_ID = 0;
    private final int COL_DATE = 1;
    private final int COL_REVIEW = 2;
    private final int COL_STARS = 3;

    public TMCustomerFeedback(List<Feedback> list) {
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
        Feedback f = new Feedback();

        if (list.isEmpty())
            return f;

        f = list.get(rowIndex);

        return switch (columnIndex) {
            case COL_ORDER_ID -> f.getOrder().getId();
            case COL_DATE -> f.getDate();
            case COL_REVIEW -> f.getReview();
            case COL_STARS -> f.getStarsAsEmote();
            default -> f;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_ORDER_ID -> "Id do Pedido";
            case COL_DATE -> "Data";
            case COL_REVIEW -> "Avaliação";
            case COL_STARS -> "Estrelas";
            default -> "";
        };
    }
}