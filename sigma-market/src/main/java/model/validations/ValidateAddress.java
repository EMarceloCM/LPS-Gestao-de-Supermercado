package model.validations;

import model.entities.Address;
import model.entities.Customer;
import model.exceptions.AddressException;

public class ValidateAddress {
    public static Address Validate(String street, String number, String complement, String neighborhood, String zipcode, Customer customer) throws AddressException {
        Address a  = new Address();

        if(street.isEmpty() || street.isBlank())
            throw new AddressException("Error - Campo vazio: 'rua'.");
        a.setStreet(street.strip());

        if(!number.matches("[0-9]*"))
            throw new AddressException("Error - Campo inválido: 'número'.");
        a.setNumber(Integer.parseInt(number));
        a.setComplement(complement.strip());

        if(neighborhood.isBlank() || neighborhood.isEmpty())
            throw new AddressException("Error - Campo vazio: 'bairro'.");
        a.setNeighborhood(neighborhood.strip());

        if(!zipcode.matches("\\d{2}\\.\\d{3}-\\d{3}"))
            throw new AddressException("Error - Campo inválido: 'zipcode'.");
        a.setZipcode(zipcode.strip());

        if(customer == null)
            throw new AddressException("Error - Campo inválido: 'customer_id'.");
        a.setCustomer(customer);




        return a;
    }
}