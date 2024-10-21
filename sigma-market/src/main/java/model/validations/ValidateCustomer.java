package model.validations;

import model.entities.Customer;
import model.exceptions.CustomerException;

public class ValidateCustomer{
    public static Customer Validate(String name, String password, String cpf, String email, String role) throws CustomerException {
        Customer c = new Customer();

        if(name.isEmpty() || name.isBlank())
            throw new CustomerException("Error - Campo vazio: 'nome'.");
        c.setName(name.strip());

        if(password.isEmpty() || password.isBlank())
            throw new CustomerException("Error - Campo vazio: 'password'.");
        c.setPassword(password.strip());

        if(cpf.isEmpty() || cpf.isBlank())
            throw new CustomerException("Error - Campo vazio: 'cpf'.");

        if(!ValidatePerson.ValidateCPF(cpf))
            throw new CustomerException("Error - Campo inválido: 'cpf'.");
        c.setCpf(cpf.strip());

        if(email.isEmpty() || email.isBlank())
            throw new CustomerException("Error - Campo vazio: 'email'.");

        if(!ValidateEmail.Validate(email))
            throw new CustomerException("Error - Campo inválido: 'email'.");
        c.setEmail(email.strip());

        if(role.isEmpty() || role.isBlank())
            throw new CustomerException("Error - Campo vazio: 'role'.");

        if(ValidateEnum.ValidateRole(role) == null)
            throw new CustomerException("Error - Campo inválido: 'role'.");

        c.setRole(ValidateEnum.ValidateRole(role));

        return c;
    }
}