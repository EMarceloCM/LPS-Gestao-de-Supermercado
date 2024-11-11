package model.validations;

import model.entities.Customer;
import model.enums.Role;
import model.exceptions.CustomerException;
import model.utils.EnumUtils;
import org.mindrot.jbcrypt.BCrypt;

public class ValidateCustomer{
    public static Customer Validate(String name, String password, String cpf, String email, String role, int profile_id) throws CustomerException {
        Customer c = new Customer();

        if(name.isEmpty() || name.isBlank())
            throw new CustomerException("Error - Campo vazio: 'nome'.");
        c.setName(name.strip());

        if(password.isEmpty() || password.isBlank())
            throw new CustomerException("Error - Campo vazio: 'password'.");
        String hashedPsw = BCrypt.hashpw(password, BCrypt.gensalt());
        c.setPassword(hashedPsw);

        if(cpf.isEmpty() || cpf.isBlank())
            throw new CustomerException("Error - Campo vazio: 'cpf'.");

        if(!ValidatePerson.ValidateCPF(cpf))
            throw new CustomerException("Error - Campo inválido: 'cpf'.");
        c.setCpf(cpf.strip());

        if(email.isEmpty() || email.isBlank())
            throw new CustomerException("Error - Campo vazio: 'email'.");

        if(!ValidateEmail.isValidEmailAddress(email))
            throw new CustomerException("Error - Campo inválido: 'email'.");
        c.setEmail(email.strip());

        if(role.isEmpty() || role.isBlank())
            throw new CustomerException("Error - Campo vazio: 'role'.");

        EnumUtils.EnumConverter<Role> converter = new EnumUtils.EnumConverter<>(Role.class);
        Role r = (Role) converter.convert(role);
        if(r == null)
            throw new CustomerException("Error - Campo inválido: 'role'.");
        c.setRole(r);

        if(profile_id < 12)
            c.setProfileId(profile_id);
        else
            throw new CustomerException("Error - Campo inválido: 'profile'");

        return c;
    }
}