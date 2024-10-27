package controller.tableModel;

import model.entities.Promotion;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMPromotion extends AbstractTableModel {
    private List<Promotion> list;

    private final int COL_DISCOUNT = 0;
    private final int COL_CREATION = 1;
    private final int COL_DURATION = 2;
    private final int COL_ACTIVE = 3;
    private final int COL_PROD_NAME = 4;

    public TMPromotion(List<Promotion> list) { this.list = list; }

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
        Promotion p = new Promotion();

        if (list.isEmpty())
            return p;

        p = list.get(rowIndex);

        return switch (columnIndex) {
            case COL_DISCOUNT -> String.format("%.2f", p.getDiscountPercentage());
            case COL_CREATION -> p.getCreationDate();
            case COL_DURATION -> p.getDurationMinutes();
            case COL_ACTIVE -> p.isActive() ? "Ativo" : "Desativada";
            case COL_PROD_NAME -> p.getProduct().getName();
            default -> p;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_DISCOUNT -> "Procentagem de Desconto";
            case COL_CREATION -> "Data da Criação";
            case COL_DURATION -> "Tempo de Duração (min.)";
            case COL_ACTIVE -> "Ativo";
            case COL_PROD_NAME -> "Produto";
            default -> "";
        };
    }
}