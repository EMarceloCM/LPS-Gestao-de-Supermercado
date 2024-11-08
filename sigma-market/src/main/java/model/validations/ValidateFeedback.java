package model.validations;

import model.entities.Customer;
import model.entities.Feedback;
import model.entities.Order;
import model.exceptions.FeedbackException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class ValidateFeedback {
    public static Feedback Validate(String review, String stars, Customer customer, Order order) throws FeedbackException, DateTimeParseException, NumberFormatException {
        Feedback f = new Feedback();

        if(review.isBlank() || review.isEmpty())
            throw new FeedbackException("Error - Campo vazio: 'feedback'.");
        f.setReview(review);
        f.setDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());

        int intValue = 0;
        try {
            intValue = Integer.parseInt(stars);
        } catch (NumberFormatException ex) {
            throw new NumberFormatException("Error - Campo inválido: 'estrelas'.");
        }
        if(intValue > 5 || intValue < 1)
            throw new FeedbackException("Error - Campo inválido: 'estrelas' deve estar entre 1 e 5.");
        f.setStars(intValue);

        if(customer == null)
            throw new FeedbackException("Error - Campo inválido: 'customer_id'.");
        f.setCustomer(customer);

        if(order == null)
            throw new FeedbackException("Error - Campo inválido: 'order_id'.");
        f.setOrder(order);

        return f;
    }
}