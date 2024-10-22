package controller;

import controller.tableModel.TMCustomer;
import controller.tableModel.TMSupplier;
import model.entities.Customer;
import model.entities.Supplier;
import model.enums.Role;
import model.exceptions.SupplierException;
import model.validations.ValidateCustomer;
import model.validations.ValidateSupplier;
import repository.CustomerRepository;

import javax.swing.*;
import java.util.List;

public class CustomerController {
    private CustomerRepository repository;

    public CustomerController() {
        repository = new CustomerRepository();
    }

    public void refreshTable(JTable t) {
        List<Customer> list = repository.findAll();
        TMCustomer model = new TMCustomer(list);
        t.setModel(model);
    }

    public void createCustomer(String cpf, String name, String email, String psw, int role) {
        Customer o = ValidateCustomer.Validate(name, psw, cpf, email, Role.values()[role].name());

        if (repository.findByCPF(o.getCpf()) != null) {
            throw new SupplierException("[ERROR] - CPF já foi cadastrado");
        }

        repository.save(o);
    }
}
