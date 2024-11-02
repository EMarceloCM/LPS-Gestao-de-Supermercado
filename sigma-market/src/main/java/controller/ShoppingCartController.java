package controller;

import Auth.SessionManager;
import controller.tableModel.TMShoppingCart;
import model.entities.Customer;
import model.entities.Product;
import model.entities.ShoppingCart;
import repository.ShoppingCartRepository;
import javax.swing.*;
import java.util.List;

public class ShoppingCartController {
    private ShoppingCartRepository repository;

    public ShoppingCartController() {
        repository = new ShoppingCartRepository();
    }

    public void refreshTable(JTable t) {
        if(SessionManager.isLoggedIn()){
            List<ShoppingCart> list = repository.findAll();
            TMShoppingCart model = new TMShoppingCart(list);
            t.setModel(model);
        }else{
            List<ShoppingCart> list = repository.findByCustomer(SessionManager.getLoggedUserId());
            TMShoppingCart model = new TMShoppingCart(list);
            t.setModel(model);
        }
    }

    public List<ShoppingCart> findByCustomer(int customer_id){
        return repository.findByCustomer(customer_id);
    }

    public void createShoppingCart(Product product, Customer customer, int quantity, float totalAmount) {
        ShoppingCart sc = new ShoppingCart(0, product, customer, quantity, totalAmount);
        repository.save(sc);
    }

    public void updateShoppingCart(int id, Product product, Customer customer, int quantity, float totalAmount) {
        ShoppingCart sc = new ShoppingCart(id, product, customer, quantity, totalAmount);
        repository.update(sc);
    }

    public void deleteShoppingCart(int id) {
        repository.delete(id);
    }
}