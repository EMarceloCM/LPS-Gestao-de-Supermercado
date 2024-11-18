package controller;

import controller.tableModel.TMItemOrder;
import model.entities.*;
import repository.ItemOrderRepository;
import javax.swing.*;
import java.util.List;

public class ItemOrderController {
    private ItemOrderRepository repository;

    public ItemOrderController() { repository = new ItemOrderRepository(); }

    public void refreshTable(JTable t) {
        List<ItemOrder> list = repository.findAll();
        TMItemOrder model = new TMItemOrder(list);
        t.setModel(model);
    }

    public List<ItemOrder> findByOrderId(int order_id) {
        return repository.findByOrder(order_id);
    }

    public void createItemOrder(Product product, Order order, int quantity, float totalAmount, float discount) {
        ItemOrder io = new ItemOrder(0, product, order, quantity, totalAmount, discount);

        repository.save(io);
    }

    public void updateItemOrder(int id, Product product, Order order, int quantity, float totalAmount, float discount) {
        ItemOrder io = new ItemOrder(id, product, order, quantity, totalAmount, discount);

        repository.update(io);
    }

    public void deleteItemOrder(int id) {
        repository.delete(id);
    }

    public void filterTableByOrder(JTable t, int order_id) {
        List<ItemOrder> list = repository.findByOrder(order_id);
        TMItemOrder model = new TMItemOrder(list);
        t.setModel(model);
    }
}