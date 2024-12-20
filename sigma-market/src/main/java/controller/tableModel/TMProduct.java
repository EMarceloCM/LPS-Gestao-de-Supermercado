package controller.tableModel;

import model.entities.Product;
import model.entities.Promotion;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TMProduct extends AbstractTableModel {
    private List<Product> list;

    private final int COL_IMAGE = 0;
    private final int COL_NAME = 1;
    private final int COL_DESC = 2;
    private final int COL_PRICE = 3;
    private final int COL_DISCOUNT = 4;
    private final int COL_SKU = 5;
    private final int COL_STOCK = 6;

    public TMProduct(List<Product> list) { this.list = list; }

    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product o = new Product();

        if (list.isEmpty())
            return o;

        o = list.get(rowIndex);

        Promotion p = null;
        boolean hasPromotion = o.getPromotions() != null && !o.getPromotions().isEmpty();
        if(hasPromotion){
            for(int i = 0; i < o.getPromotions().size(); i++) {
                if(o.getPromotions().get(i).getActive() == 1){
                    p = o.getPromotions().get(i);
                    break;
                }
            }
        }
        hasPromotion = p != null;

        return switch (columnIndex) {
            case COL_IMAGE -> o.getImgUrl();
            case COL_NAME -> o.getName();
            case COL_DESC -> o.getDescription();
            case COL_PRICE -> o.getPrice();
            case COL_DISCOUNT -> !hasPromotion ? 0 : p.getFinalPrice();
            case COL_SKU -> o.getSku();
            case COL_STOCK -> o.getStock();
            default -> o;
        };
    }

    @Override
    public String getColumnName(int column) {
        return switch (column) {
            case COL_IMAGE -> "Imagem";
            case COL_NAME -> "Nome";
            case COL_DESC -> "Desc";
            case COL_PRICE -> "Preco";
            case COL_DISCOUNT -> "Preço c/ Desconto";
            case COL_SKU -> "SKU";
            case COL_STOCK -> "Estoque";
            default -> "";
        };
    }
}