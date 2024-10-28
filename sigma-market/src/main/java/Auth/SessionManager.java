package Auth;

import Auth.exceptions.AuthException;
import model.entities.Customer;
import model.enums.Role;
import model.exceptions.CustomerException;
import repository.CustomerRepository;

import java.util.Objects;

public class SessionManager {
    private static Customer customer = null;
    private static boolean isLoggedIn = false;

    public static boolean isLoggedIn(){
        return SessionManager.isLoggedIn;
    }

    public static void Login(String email, String psw) throws AuthException {
        try {
            Customer customer = new CustomerRepository().findByEmail(email);

            if(customer != null && Objects.equals(customer.getPassword(), psw)){
                SessionManager.customer = customer;
                SessionManager.isLoggedIn = true;
            } else throw new CustomerException("[Error] - Usuário informado não existe.");
        }catch (CustomerException ex){
            throw new AuthException(ex.getMessage());
        }
    }

    public static void Logout(){
        SessionManager.customer = null;
        SessionManager.isLoggedIn = false;
    }

    public static Customer getLoggedUser() throws AuthException {
        if (SessionManager.isLoggedIn) return SessionManager.customer;
        throw new AuthException("[Error] - Nenhum usuário logado.");
    }

    public static Role getLoggedUserRole() throws AuthException {
        if(SessionManager.isLoggedIn) return SessionManager.customer.getRole();
        throw new AuthException("[Error] - Nenhum usuário logado.");
    }

    public static int getLoggedUserId() throws AuthException {
        if(SessionManager.isLoggedIn) return SessionManager.customer.getId();
        throw new AuthException("[Error] - Nenhum usuário logado.");
    }
}