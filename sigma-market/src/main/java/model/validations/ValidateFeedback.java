package model.validations;

import model.entities.Customer;
import model.entities.Feedback;
import model.entities.Order;
import model.exceptions.FeedbackException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ValidateFeedback {
    public static Feedback Validate(String review, Customer customer, Order order) throws FeedbackException {
        Feedback f = new Feedback();

        if(review.isBlank() || review.isEmpty())
            throw new FeedbackException("Error - Campo vazio: 'feedback'.");
        f.setReview(review);
        f.setDate(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());

        if(customer == null)
            throw new FeedbackException("Error - Campo inválido: 'customer_id'.");
        f.setCustomer(customer);

        if(order == null)
            throw new FeedbackException("Error - Campo inválido: 'order_id'.");
        f.setOrder(order);

        return f;
    }
}