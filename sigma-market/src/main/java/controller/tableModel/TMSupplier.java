package controller.tableModel;

import lombok.Getter;
import model.entities.Supplier;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class TMSupplier extends AbstractTableModel {
    @Getter
    private List<Supplier> list;

    private final int COL_NAME = 0;
    private final int COL_CNPJ = 1;

    public TMSupplier(List<Supplier> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Supplier o = new Supplier();

        if (list.isEmpty())
            return o;

        o = (Supplier) list.get(rowIndex);

        return switch (columnIndex) {
            case COL_NAME -> o.getName();
            case COL_CNPJ -> o.getCnpj();
            default -> o;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_NAME -> "Nome";
            case COL_CNPJ -> "CNPJ";
            default -> "";
        };
    }
}