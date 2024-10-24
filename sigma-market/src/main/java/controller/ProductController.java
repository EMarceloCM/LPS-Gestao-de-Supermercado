package controller;

import controller.tableModel.TMProduct;
import controller.tableModel.TMProductBuy;
import model.entities.Product;
import model.entities.Promotion;
import model.validations.ValidateProduct;
import repository.ProductRepository;
import repository.PromotionRepository;

import javax.swing.*;
import java.util.List;

public class ProductController {
    private ProductRepository repository;
    private PromotionRepository promotionRepository;

    public ProductController() {
        repository = new ProductRepository();
    }

    // tabela da página inicial de compras
    public void refreshBuyTable(JTable t) {
        List list = repository.findAll();
        TMProductBuy model = new TMProductBuy(list);
        t.setModel(model);
    }

    public void refreshTable(JTable t) {
        List list = repository.findAll();
        List<Promotion> promotionList = promotionRepository.findActive(true);
        TMProduct model = new TMProduct(list, promotionList);
        t.setModel(model);
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

    public void deleteCustomer(int id) {
        repository.delete(id);
    }

    public void filterTable(JTable t, String filter) {
        List<Product> list = repository.findWithFilter(filter);
        List<Promotion> promotionList = promotionRepository.findActive(true);
        TMProduct model = new TMProduct(list, promotionList);
        t.setModel(model);
    }
}