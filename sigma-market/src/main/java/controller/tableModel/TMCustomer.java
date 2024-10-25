package controller.tableModel;

import model.entities.Customer;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMCustomer extends AbstractTableModel {
    private List<Customer> list;

    private final int COL_NAME = 0;
    private final int COL_PSW = 1;
    private final int COL_CPF = 2;
    private final int COL_EMAIL = 3;
    private final int COL_ROLE = 4;

    public TMCustomer(List<Customer> list) {
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
        Customer o = new Customer();

        if (list.isEmpty())
            return o;

        o = list.get(rowIndex);

        return switch (columnIndex) {
            case COL_NAME -> o.getName();
            case COL_PSW -> o.getPassword();
            case COL_CPF -> o.getCpf();
            case COL_EMAIL -> o.getEmail();
            case COL_ROLE -> o.getRole().name();
            default -> o;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_NAME -> "Nome";
            case COL_PSW -> "Senha";
            case COL_CPF -> "CPF";
            case COL_EMAIL -> "Email";
            case COL_ROLE -> "Cargo";
            default -> "";
        };
    }
}