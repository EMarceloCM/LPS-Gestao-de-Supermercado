package model.validations;

import model.entities.Product;
import model.exceptions.ProductException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class ValidateProduct {
    public static Product Validate(String name, String description, String imgUrl, String price, int stock, String sku) throws ProductException, DateTimeParseException, NumberFormatException {
        Product p = new Product();

        if (name.isBlank() || name.isEmpty())
            throw new ProductException("Error - Campo vazio: 'nome'.");
        p.setName(name.strip());

        if(description.isBlank() || description.isEmpty())
            throw new ProductException("Error - Campo vazio: 'descrição'.");
        p.setDescription(description.strip());

        if(imgUrl.isBlank() || imgUrl.isEmpty())
            throw new ProductException("Error - Campo vazio: 'ULR da imagem'.");
        p.setImgUrl(imgUrl);

        float floatValue = 0.0f;
        try {
            floatValue = Float.parseFloat(price);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Error - Campo inválido: 'preço'.");
        }
        if(floatValue == 0.0f)
            throw new ProductException("Error - Campo inválido: 'preço' não pode ser 0.");
        p.setPrice(floatValue);

        if (stock < 0) {
            throw new NumberFormatException("Error - Campo inválido: 'estoque'.");
        }
        p.setStock(stock);

        if(sku.isBlank() || sku.isEmpty())
            throw new ProductException("Error - Campo vazio: 'sku'.");
        p.setSku(sku);

        return p;
    }
}