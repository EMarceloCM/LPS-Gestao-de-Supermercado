package controller;

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
        List<ShoppingCart> list = repository.findAll();
        TMShoppingCart model = new TMShoppingCart(list);
        t.setModel(model);
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