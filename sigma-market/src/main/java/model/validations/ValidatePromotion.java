package model.validations;

import model.entities.Product;
import model.entities.Promotion;
import model.exceptions.PromotionException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class ValidatePromotion {
    public static Promotion Validate(String discountPercentage, String durationMinutes, boolean isActive, Product product) throws PromotionException, NumberFormatException, DateTimeParseException {
        Promotion p = new Promotion();

        float floatValue = 0.0f;
        try {
            floatValue = Float.parseFloat(discountPercentage);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Error - Campo inválido: 'percentual de desconto'.");
        }
        if(floatValue == 0.0f)
            throw new PromotionException("Error - Campo inválido: 'percentual de desconto' não pode ser 0.");
        p.setDiscountPercentage(floatValue);

        p.setCreationDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());

        int intValue = 0;
        try {
            intValue = Integer.parseInt(durationMinutes);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Error - Campo inválido: 'duração em minutos'.");
        }
        if(intValue == 0)
            throw new PromotionException("Error - Campo inválido: 'duração em minutos' não pode ser 0.");
        p.setDurationMinutes(intValue);

        p.setActive(isActive ? 1 : 0);

        if(product == null)
            throw new PromotionException("Error - Campo inválido: 'product_id'.");
        p.setProduct(product);

        return p;
    }
}