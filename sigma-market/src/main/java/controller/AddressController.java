package controller;

import controller.tableModel.TMAddress;
import model.entities.Address;
import model.entities.Customer;
import model.validations.ValidateAddress;
import repository.AddressRepository;
import javax.swing.*;
import java.util.List;

public class AddressController {
    private AddressRepository repository;

    public AddressController() {
        repository = new AddressRepository();
    }

    public void refreshTable(JTable t) {
        List<Address> list = repository.findAll();
        TMAddress model = new TMAddress(list);
        t.setModel(model);
    }

    public void filterTable(JTable t, String filter) {
        List<Address> list = repository.findWithFilter(filter);
        TMAddress model = new TMAddress(list);
        t.setModel(model);
    }

    public void createAddress(String street, String number, String complement, String neighborhood, Customer customer) {
        Address a = ValidateAddress.Validate(street, number, complement, neighborhood, customer);
        repository.save(a);
    }

    public void updateAddress(int id, String street, String number, String complement, String neighborhood, Customer customer) {
        Address a = ValidateAddress.Validate(street, number, complement, neighborhood, customer);
        a.setId(id);

        repository.update(a);
    }

    public void deleteAddress(int id) {
        repository.delete(id);
    }

    public void filterTableByCustomer(JTable t, int customer_id) {
        List<Address> list = repository.findByCostumer(customer_id);
        TMAddress model = new TMAddress(list);
        t.setModel(model);
    }
}