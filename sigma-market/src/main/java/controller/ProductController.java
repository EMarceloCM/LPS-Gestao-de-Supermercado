package controller;

import controller.tableModel.TMProduct;
import controller.tableModel.TMProductBuy;
import model.entities.Product;
import model.validations.ValidateProduct;
import repository.ProductRepository;
import javax.swing.*;
import java.util.List;

public class ProductController {
    private ProductRepository repository;

    public ProductController() {
        repository = new ProductRepository();
    }

    public void refreshBuyTable(JTable t) {
        List<Product> list = repository.findAll();
        TMProductBuy model = new TMProductBuy(list);
        t.setModel(model);
    }

    public void refreshTable(JTable t) {
        List<Product> list = repository.findAll();
        TMProduct model = new TMProduct(list);
        t.setModel(model);
    }

    public void filterTable(JTable t, String filter) {
        List<Product> list = repository.findWithFilter(filter);
        TMProduct model = new TMProduct(list);
        t.setModel(model);
    }

    public void filterBuyTable(JTable t, String filter) {
        List<Product> list = repository.findWithFilter(filter);
        TMProductBuy model = new TMProductBuy(list);
        t.setModel(model);
    }

    public Product findProductById(int product_id){
        return repository.find(product_id);
    }

    public void createProduct(String name, String description, String imgUrl, String price, int stock, String sku) {
        Product o = ValidateProduct.Validate(name, description, imgUrl, price, stock, sku);
        repository.save(o);
    }

    public void updateProduct(int id, String name, String description, String imgUrl, String price, int stock, String sku) {
        Product o = ValidateProduct.Validate(name, description, imgUrl, price, stock, sku);
        o.setId(id);
        repository.update(o);
    }

    public void deleteProduct(int id) {
        repository.delete(id);
    }
}