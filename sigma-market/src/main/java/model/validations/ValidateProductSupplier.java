package model.validations;

import model.entities.Product;
import model.entities.ProductSupplier;
import model.entities.Supplier;
import model.exceptions.ProductSupplierException;
import model.exceptions.PromotionException;
import model.exceptions.SupplierException;

import java.time.LocalDateTime;

public class ValidateProductSupplier {
    public static ProductSupplier Validate(Product product, Supplier supplier, String purchasePrice, String quantity, LocalDateTime date) throws ProductSupplierException {
        ProductSupplier ps = new ProductSupplier();

        if(product == null)
            throw new ProductSupplierException("Error - produto invalido.");
        ps.setProduct(product);

        if(supplier == null)
            throw new ProductSupplierException("Error - fornecedor invalido.");
        ps.setSupplier(supplier);

        float floatValue = 0.0f;
        try {
            floatValue = Float.parseFloat(purchasePrice);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Error - Campo inválido: 'preço'.");
        }
        ps.setPurchasePrice(floatValue);

        int intValue = 0;
        try {
            intValue = Integer.parseInt(quantity);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Error - Campo inválido: 'qtd'.");
        }
        ps.setQuantity(intValue);

        ps.setPurchaseDate(date);

        return ps;
    }
}