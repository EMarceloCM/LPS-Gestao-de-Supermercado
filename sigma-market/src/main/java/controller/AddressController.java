package controller;

import Auth.SessionManager;
import controller.tableModel.TMAddress;
import controller.tableModel.TMCustomerAddress;
import model.entities.Address;
import model.entities.Customer;
import model.enums.Role;
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
        if(SessionManager.getLoggedUserRole() == Role.ADMIN) {
            List<Address> list = repository.findAll();
            TMAddress model = new TMAddress(list);
            t.setModel(model);
        } else if(SessionManager.getLoggedUserRole() == Role.CUSTOMER){
            List<Address> list = repository.findByCostumer(SessionManager.getLoggedUserId());
            TMCustomerAddress model = new TMCustomerAddress(list);
            t.setModel(model);
        }
    }

    public void filterTable(JTable t, String filter) {
        if(SessionManager.getLoggedUserRole() == Role.ADMIN) {
            List<Address> list = repository.findWithFilter(filter);
            TMAddress model = new TMAddress(list);
            t.setModel(model);
        } else if(SessionManager.getLoggedUserRole() == Role.CUSTOMER){
            List<Address> list = repository.findWithFilter(filter);
            TMCustomerAddress model = new TMCustomerAddress(list);
            t.setModel(model);
        }
    }

    public List<Address> findByCustomer(int customer_id){
        return repository.findByCostumer(customer_id);
    }

    public void createAddress(String street, String number, String complement, String neighborhood, String zipcode, Customer customer) {
        Address a = ValidateAddress.Validate(street, number, complement, neighborhood, zipcode, customer);
        repository.save(a);
    }

    public void updateAddress(int id, String street, String number, String complement, String neighborhood, String zipcode, Customer customer) {
        Address a = ValidateAddress.Validate(street, number, complement, neighborhood, zipcode, customer);
        a.setId(id);

        repository.update(a);
    }

    public void deleteAddress(int id) { repository.delete(id); }

    public void filterTableByCustomer(JTable t, int customer_id) {
        List<Address> list = repository.findByCostumer(customer_id);
        TMAddress model = new TMAddress(list);
        t.setModel(model);
    }
}