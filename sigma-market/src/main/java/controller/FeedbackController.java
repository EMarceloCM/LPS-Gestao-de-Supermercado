package controller;

import Auth.SessionManager;
import controller.tableModel.TMCustomerFeedback;
import controller.tableModel.TMFeedback;
import model.entities.Customer;
import model.entities.Feedback;
import model.entities.Order;
import model.enums.Role;
import model.validations.ValidateFeedback;
import repository.FeedbackRepository;
import javax.swing.*;
import java.util.List;

public class FeedbackController {
    private FeedbackRepository repository;

    public FeedbackController() {
        repository = new FeedbackRepository();
    }

    public void refreshTable(JTable t) {
        if(SessionManager.getLoggedUserRole() == Role.ADMIN) {
            List<Feedback> list = repository.findAll();
            TMFeedback model = new TMFeedback(list);
            t.setModel(model);
        } else if(SessionManager.getLoggedUserRole() == Role.CUSTOMER) {
            List<Feedback> list = repository.findByCustomer(SessionManager.getLoggedUserId());
            TMCustomerFeedback model = new TMCustomerFeedback(list);
            t.setModel(model);
        }
    }

    public void filterTable(JTable t, String filter) {
        if(SessionManager.getLoggedUserRole() == Role.ADMIN) {
            List<Feedback> list = repository.findWithFilter(filter);
            TMFeedback model = new TMFeedback(list);
            t.setModel(model);
        } else if(SessionManager.getLoggedUserRole() == Role.CUSTOMER) {
            List<Feedback> list = repository.findWithFilter(filter);
            TMCustomerFeedback model = new TMCustomerFeedback(list);
            t.setModel(model);
        }
    }

    public void createFeedback(String review, String stars, Customer customer, Order order) {
        Feedback f = ValidateFeedback.Validate(review, stars, customer, order);
        repository.save(f);
    }

    public void updateFeedback(int id, String review, String stars, Customer customer, Order order) {
        Feedback f = ValidateFeedback.Validate(review, stars, customer, order);
        f.setId(id);

        repository.update(f);
    }

    public void deleteFeedback(int id) {
        repository.delete(id);
    }

    public void filterTableByCustomer(JTable t, int customer_id) {
        List<Feedback> list = repository.findByCustomer(customer_id);
        TMFeedback model = new TMFeedback(list);
        t.setModel(model);
    }

    public Feedback findByOrderId(int order_id) {
        return repository.findByOrder(order_id);
    }
}