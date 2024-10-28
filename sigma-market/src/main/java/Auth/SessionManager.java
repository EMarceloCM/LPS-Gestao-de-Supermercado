package Auth;

import Auth.exceptions.AuthException;
import model.entities.Customer;
import model.enums.Role;
import model.exceptions.CustomerException;
import repository.CustomerRepository;

public class SessionManager {
    private static Customer customer = null;
    private static boolean isLoggedIn = false;

    public boolean isLoggedIn(){
        return SessionManager.isLoggedIn;
    }

    public void Login(Customer customer) throws AuthException {
        try {
            Customer finalCustomer = new CustomerRepository().find(customer.getId());

            if(finalCustomer != null){
                SessionManager.customer = customer;
                SessionManager.isLoggedIn = true;
            } else throw new CustomerException("[Error] - Usuário informado não existe.");
        }catch (CustomerException ex){
            throw new AuthException(ex.getMessage());
        }
    }

    public void Logout(){
        SessionManager.customer = null;
        SessionManager.isLoggedIn = false;
    }

    public Role getLoggedUserRole() throws AuthException {
        if(SessionManager.isLoggedIn) return SessionManager.customer.getRole();
        throw new AuthException("[Error] - Nenhum usuário logado.");
    }

    public Customer getLoggedUser() throws AuthException {
        if (SessionManager.isLoggedIn) return SessionManager.customer;
        throw new AuthException("[Error] - Nenhum usuário logado.");
    }
}