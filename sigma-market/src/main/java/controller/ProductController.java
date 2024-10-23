package controller;

import controller.tableModel.TMProduct;
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
        TMProduct model = new TMProduct(list);
        t.setModel(model);
    }
}