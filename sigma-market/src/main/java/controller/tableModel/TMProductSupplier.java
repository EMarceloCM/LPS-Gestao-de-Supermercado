package controller.tableModel;

import model.entities.ProductSupplier;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMProductSupplier extends AbstractTableModel {
    private List<ProductSupplier> list;

    private final int COL_ID = 0;
    private final int COL_PROD = 1;
    private final int COL_SUP = 2;
    private final int COL_PCH_PRICE = 3;
    private final int COL_QUANTITY = 4;
    private final int COL_PCH_DATE = 5;

    public TMProductSupplier(List<ProductSupplier> list) { this.list = list; }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProductSupplier ps = new ProductSupplier();

        if(list.isEmpty()) return ps;

        ps = list.get(rowIndex);

        return switch (columnIndex) {
            case COL_ID -> ps.getId();
            case COL_PROD -> ps.getProduct().getId() + ": " + ps.getProduct().getName();
            case COL_SUP -> ps.getSupplier().getId() + ": " + ps.getSupplier().getName();
            case COL_PCH_PRICE -> ps.getPurchasePrice();
            case COL_QUANTITY -> ps.getQuantity();
            case COL_PCH_DATE -> ps.getPurchaseDate();
            default -> ps;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_ID -> "Id";
            case COL_PROD -> "Produto";
            case COL_SUP -> "Fornecedor";
            case COL_PCH_PRICE -> "Preço";
            case COL_QUANTITY -> "Quantidade";
            case COL_PCH_DATE -> "Data da Compra";
            default -> "";
        };
    }
}