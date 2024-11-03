package controller;

import Auth.SessionManager;
import controller.tableModel.TMAdminCustomer;
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
        if(SessionManager.getLoggedUserRole() == Role.ADMIN) {
            List<Customer> list = repository.findAll();
            TMAdminCustomer model = new TMAdminCustomer(list);
            t.setModel(model);
        }
    }

    public void filterTable(JTable t, String filter) {
        List<Customer> list = repository.findWithFilter(filter);
        TMAdminCustomer model = new TMAdminCustomer(list);
        t.setModel(model);
    }

    public void createCustomer(String cpf, String email, String name, String psw, int role) {
        Customer o = ValidateCustomer.Validate(name, psw, cpf, email, Role.values()[role].name(), 0);

        if (repository.findByCPF(o.getCpf()) != null) {
            throw new CustomerException("[ERROR] - Este CPF já foi cadastrado!");
        }

        if (repository.findByEmail(o.getEmail()) != null) {
            throw new CustomerException("[ERROR] - Este E-mail já foi cadastrado!");
        }

        repository.save(o);
    }

    public void updateCustomer(int id, String cpf, String email, String name, String psw, int role, int profile_id) {
        Customer o = ValidateCustomer.Validate(name, psw, cpf, email, Role.values()[role].name(), profile_id);
        o.setId(id);

        Customer r = repository.findByCPF(o.getCpf());
        if (r != null && r.getId() != id) {
            throw new CustomerException("[ERROR] - Este CPF já foi cadastrado");
        }

        r = repository.findByEmail(o.getEmail());
        if (r != null && r.getId() != id) {
            throw new CustomerException("[ERROR] - Este E-mail já foi cadastrado!");
        }

        repository.update(o);
    }

    public void deleteCustomer(int id) {
        repository.delete(id);
    }
}