package model.validations;

import model.entities.Supplier;
import model.exceptions.SupplierException;

public class ValidateSupplier {
    public static Supplier Validate(String cnpj, String name) throws SupplierException {
        Supplier s = new Supplier();

        if(name.isEmpty() || name.isBlank())
            throw new SupplierException("Error - Campo vazio: 'nome'.");
        s.setName(name.strip());

        if(cnpj.isEmpty() || cnpj.isBlank())
            throw new SupplierException("Error - Campo vazio: 'cnpj'.");

        if(!ValidateLegalEntity.ValidateCNPJ(cnpj))
            throw new SupplierException("Error - Campo inválido: 'cnpj'.");
        s.setCnpj(cnpj.strip());

        return s;
    }
}