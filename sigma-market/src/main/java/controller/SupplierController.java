package controller;

import controller.tableModel.TMSupplier;
import model.entities.Customer;
import model.entities.Supplier;
import model.exceptions.SupplierException;
import model.validations.ValidateSupplier;
import repository.SupplierRepository;
import javax.swing.*;
import java.util.List;

public class SupplierController {
    private SupplierRepository repository;

    public SupplierController() {
        repository = new SupplierRepository();
    }

    public void refreshTable(JTable t) {
        List<Supplier> list = repository.findAll();
        TMSupplier model = new TMSupplier(list);
        t.setModel(model);
    }

    public void filterTable(JTable t, String filter) {
        List<Supplier> list = repository.findWithFilter(filter);
        TMSupplier model = new TMSupplier(list);
        t.setModel(model);
    }

    public void createSupplier(String cnpj, String nome) {
        Supplier newObj = ValidateSupplier.Validate(cnpj, nome);

        if (repository.findByCNPJ(newObj.getCnpj()) != null) {
            throw new SupplierException("[ERROR] - O Fornecedor com esse CNPJ já foi cadastrado");
        }

        repository.save(newObj);
    }

    public void updateSupplier(int id, String cnpj, String nome) {
        Supplier o = ValidateSupplier.Validate(cnpj, nome);
        o.setId(id);

        Supplier r = repository.findByCNPJ(o.getCnpj());
        if (r != null && r.getId() != id) {
            throw new SupplierException("[ERROR] - O Fornecedor com esse CNPJ já foi cadastrado");
        }

        repository.update(o);
    }

    public void deleteSupplier(int id) {
        repository.delete(id);
    }
}