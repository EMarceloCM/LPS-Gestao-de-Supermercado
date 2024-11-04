package controller.tableModel;

import model.entities.ItemOrder;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMItemOrder extends AbstractTableModel {
    private List<ItemOrder> lst;

    private final int COL_ID = 0;
    private final int COL_PRD_NAME = 1;
    private final int COL_QUANTITY = 2;
    private final int COL_TOTAL_AMOUNT = 3;

    public TMItemOrder(List<ItemOrder> lst) {
        this.lst = lst;
    }

    @Override
    public int getRowCount() {
        return lst.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemOrder io = new ItemOrder();

        if(lst.isEmpty()) return io;
        io = lst.get(rowIndex);

        return switch (columnIndex) {
            case COL_ID -> io.getId();
            case COL_PRD_NAME -> io.getProduct().getName();
            case COL_QUANTITY -> io.getQuantity();
            case COL_TOTAL_AMOUNT -> io.getTotalAmount();
            default -> io;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_ID -> "Id";
            case COL_PRD_NAME -> "Produto";
            case COL_QUANTITY -> "Quantidade";
            case COL_TOTAL_AMOUNT -> "Valor Total";
            default -> "";
        };
    }
}