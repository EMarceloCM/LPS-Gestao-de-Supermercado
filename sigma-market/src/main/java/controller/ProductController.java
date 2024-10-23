package controller;

import controller.tableModel.TMProductBuy;
import repository.ProductRepository;
import javax.swing.*;
import java.util.List;

public class ProductController {
    private ProductRepository repository;

    public ProductController() {
        repository = new ProductRepository();
    }

    public void refreshBuyTable(JTable t) {
        List list = repository.findAll();
        TMProductBuy model = new TMProductBuy(list);
        t.setModel(model);
    }
}