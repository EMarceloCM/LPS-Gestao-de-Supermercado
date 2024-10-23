package controller;

import controller.tableModel.TMCustomer;
import model.entities.Customer;
import model.enums.Role;
import model.exceptions.CustomerException;
import model.validations.ValidateCustomer;
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

    public void filterTable(JTable t, String filter) {
        List<Customer> list = repository.findWithFilter(filter);
        TMCustomer model = new TMCustomer(list);
        t.setModel(model);
    }

    public void createCustomer(String cpf, String email, String name, String psw, int role) {
        Customer o = ValidateCustomer.Validate(name, psw, cpf, email, Role.values()[role].name());

        if (repository.findByCPF(o.getCpf()) != null) {
            throw new CustomerException("[ERROR] - Este CPF já foi cadastrado!");
        }

        if (repository.findByEmail(o.getEmail()) != null) {
            throw new CustomerException("[ERROR] - Este E-mail já foi cadastrado!");
        }

        repository.save(o);
    }

    public void updateCustomer(int id, String cpf, String email, String name, String psw, int role) {
        Customer o = ValidateCustomer.Validate(name, psw, cpf, email, Role.values()[role].name());
        o.setId(id);

        if (repository.findByCPF(o.getCpf()).getId() != id) {
            throw new CustomerException("[ERROR] - Este CPF já foi cadastrado");
        }

        if (repository.findByEmail(o.getEmail()).getId() != id) {
            throw new CustomerException("[ERROR] - Este E-mail já foi cadastrado!");
        }

        repository.update(o);
    }

    public void deleteCustomer(int id) {
        repository.delete(id);
    }
}