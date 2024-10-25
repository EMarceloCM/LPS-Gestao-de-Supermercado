package controller.tableModel;

import model.entities.Address;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMCustomerAddress extends AbstractTableModel {
    private List<Address> list;

    private final int COL_STREET = 0;
    private final int COL_NUMBER = 1;
    private final int COL_COMPLEMENT = 2;
    private final int COL_NEIGHBORHOOD = 3;

    public TMCustomerAddress(List<Address> list) {
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
        Address a = new Address();

        if (list.isEmpty())
            return a;

        a = list.get(rowIndex);

        return switch (columnIndex) {
            case COL_STREET -> a.getStreet();
            case COL_NUMBER -> a.getNumber();
            case COL_COMPLEMENT -> a.getComplement();
            case COL_NEIGHBORHOOD -> a.getNeighborhood();
            default -> a;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_STREET -> "Rua";
            case COL_NUMBER -> "Número";
            case COL_COMPLEMENT -> "Complemento";
            case COL_NEIGHBORHOOD -> "Bairro";
            default -> "";
        };
    }
}