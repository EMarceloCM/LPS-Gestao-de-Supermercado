package controller.tableModel;

import model.entities.Customer;
import javax.swing.table.AbstractTableModel;
//TODO deletar se nao for utilizar
public class TMCustomer extends AbstractTableModel {
    private Customer c;

    private final int COL_NAME = 0;
    private final int COL_PSW = 1;
    private final int COL_CPF = 2;
    private final int COL_EMAIL = 3;

    public TMCustomer(Customer c) {
        this.c = c;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return switch (columnIndex) {
            case COL_NAME -> c.getName();
            case COL_PSW -> c.getPassword();
            case COL_CPF -> c.getCpf();
            case COL_EMAIL -> c.getEmail();
            default -> c;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_NAME -> "Nome";
            case COL_PSW -> "Senha";
            case COL_CPF -> "CPF";
            case COL_EMAIL -> "Email";
            default -> "";
        };
    }
}