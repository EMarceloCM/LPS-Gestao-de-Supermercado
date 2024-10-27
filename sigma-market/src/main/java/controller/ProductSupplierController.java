package controller;

import controller.tableModel.TMProductSupplier;
import model.entities.Product;
import model.entities.ProductSupplier;
import model.entities.Supplier;
import repository.ProductSupplierRepository;
import javax.swing.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class ProductSupplierController {
    private ProductSupplierRepository repository;

    ProductSupplierController() { repository = new ProductSupplierRepository(); }

    public void refreshTable(JTable t) {
        List<ProductSupplier> list = repository.findAll();
        TMProductSupplier model = new TMProductSupplier(list);
        t.setModel(model);
    }

    public void createProductSupplier(Product product, Supplier supplier, float purchasePrice, int quantity) {
        ProductSupplier ps = new ProductSupplier(0, product, supplier, purchasePrice, quantity, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());

        repository.save(ps);
    }

    public void updateProductSupplier(int id, Product product, Supplier supplier, float purchasePrice, int quantity) {
        ProductSupplier ps = new ProductSupplier(id, product, supplier, purchasePrice, quantity, ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());

        repository.update(ps);
    }

    public void deleteProductSupplier(int id) {
        // TODO confirmar se não irá deletar producto/fornecedor juntos
        repository.delete(id);
    }

    public void filterTable(JTable t, String filter) {
        List<ProductSupplier> list = repository.findWithFilter(filter);
        TMProductSupplier model = new TMProductSupplier(list);
        t.setModel(model);
    }
}