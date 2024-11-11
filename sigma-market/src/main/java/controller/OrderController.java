package controller;

import Auth.SessionManager;
import controller.tableModel.TMCustomerOrder;
import controller.tableModel.TMOrder;
import model.entities.Address;
import model.entities.Customer;
import model.entities.Order;
import model.enums.OrderStatus;
import model.enums.PaymentStatus;
import model.enums.Role;
import model.exceptions.OrderException;
import model.validations.ValidateOrder;
import repository.OrderRepository;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public class OrderController {
    private OrderRepository repository;

    public OrderController() {
        repository = new OrderRepository();
    }

    public void refreshTable(JTable t) {
        if(SessionManager.getLoggedUserRole() == Role.ADMIN) {
            List<Order> list = repository.findAll();
            TMOrder model = new TMOrder(list);
            t.setModel(model);
        } else if (SessionManager.getLoggedUserRole() == Role.CUSTOMER) {
            List<Order> list = repository.findByCustomer(SessionManager.getLoggedUserId());
            TMCustomerOrder model = new TMCustomerOrder(list);
            t.setModel(model);
        }
    }

    public Order findById(int id) {
        return repository.find(id);
    }

    public void createOrder(String totalAmount, String paymentType, Customer customer, Address address) {
        Order o = ValidateOrder.Validate(totalAmount, OrderStatus.APPROVED.toString(), PaymentStatus.PENDIND.toString(), paymentType, customer, address);
        repository.save(o);
    }

    public Order createOrderAndGet(String totalAmount, String paymentType, Customer customer, Address address) {
        Order o = ValidateOrder.Validate(totalAmount, OrderStatus.APPROVED.toString(), PaymentStatus.PENDIND.toString(), paymentType, customer, address);
        return repository.saveAndGet(o);
    }

    public void updateOrder(int id, String totalAmount, String orderStatus, String paymentStatus, String paymentType, Customer customer, Address address) {
        Order o = ValidateOrder.Validate(totalAmount, orderStatus, paymentStatus, paymentType, customer, address);
        o.setId(id);

        repository.update(o);
    }

    public void deleteOrder(int id) {
        repository.delete(id);
    }

    public void filterTableByCustomer(JTable t, int customer_id) {
        List<Order> list = repository.findByCustomer(customer_id);
        TMOrder model = new TMOrder(list);
        t.setModel(model);
    }

    public List<Order> findWithinDateRange(LocalDateTime start, LocalDateTime end){
        if (start.isAfter(end)) {
            throw new OrderException("Error - a data inicial deve ser anterior a data final");
        }
        return repository.findOrdersWithinDateRange(start, end);
    }
}