package controller;

import controller.tableModel.TMProductSupplier;
import model.entities.Product;
import model.entities.ProductSupplier;
import model.entities.Supplier;
import model.validations.ValidateProductSupplier;
import repository.ProductSupplierRepository;
import javax.swing.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class ProductSupplierController {
    private ProductSupplierRepository repository;

    public ProductSupplierController() { repository = new ProductSupplierRepository(); }

    public void refreshTable(JTable t) {
        List<ProductSupplier> list = repository.findAll();
        TMProductSupplier model = new TMProductSupplier(list);
        t.setModel(model);
    }

    public void createProductSupplier(Product product, Supplier supplier, String purchasePrice, String quantity) {
        ProductSupplier ps = ValidateProductSupplier.Validate(product, supplier, purchasePrice, quantity, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
        repository.save(ps);
    }

    public void updateProductSupplier(int id, Product product, Supplier supplier, String purchasePrice, String quantity) {
        ProductSupplier ps = ValidateProductSupplier.Validate(product, supplier, purchasePrice, quantity, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
        ps.setId(id);

        repository.update(ps);
    }

    public void deleteProductSupplier(int id) { repository.delete(id); }

    public void filterTable(JTable t, String filter) {
        List<ProductSupplier> list = repository.findWithFilter(filter);
        TMProductSupplier model = new TMProductSupplier(list);
        t.setModel(model);
    }
}