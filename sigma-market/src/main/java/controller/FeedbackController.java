package controller;

import controller.tableModel.TMAddress;
import controller.tableModel.TMCustomerAddress;
import controller.tableModel.TMCustomerFeedback;
import controller.tableModel.TMFeedback;
import model.entities.Address;
import model.entities.Customer;
import model.entities.Feedback;
import model.entities.Order;
import model.validations.ValidateAddress;
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
        List<Feedback> list = repository.findAll();
        TMFeedback model = new TMFeedback(list);
        t.setModel(model);
    }
    
    public void refreshCustomerTable(JTable t, int customer_id) {
        List<Feedback> list = repository.findByCustomer(customer_id);
        TMCustomerFeedback model = new TMCustomerFeedback(list);
        t.setModel(model);
    }

    public void filterTable(JTable t, String filter) {
        List<Feedback> list = repository.findWithFilter(filter);
        TMFeedback model = new TMFeedback(list);
        t.setModel(model);
    }

    public void filterCustomerTable(JTable t, String filter) {
        List<Feedback> list = repository.findWithFilter(filter);
        TMCustomerFeedback model = new TMCustomerFeedback(list);
        t.setModel(model);
    }

    public void createFeedback(String review, Customer customer, Order order) {
        Feedback f = ValidateFeedback.Validate(review, customer, order);
        repository.save(f);
    }

    public void updateFeedback(int id, String review, Customer customer, Order order) {
        Feedback f = ValidateFeedback.Validate(review, customer, order);
        f.setId(id);

        repository.update(f);
    }

    //TODO confirmar que não irá deletar o pedido junto
    public void deleteFeedback(int id) {
        repository.delete(id);
    }

    public void filterTableByCustomer(JTable t, int customer_id) {
        List<Feedback> list = repository.findByCustomer(customer_id);
        TMFeedback model = new TMFeedback(list);
        t.setModel(model);
    }
}