package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.Customer;
import repository.interfaces.IRepository;
import java.util.List;

public class CustomerRepository implements IRepository<Customer> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public CustomerRepository() {}

    @Override
    public Customer find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Customer c = this.entityManager.find(Customer.class, id);
        this.entityManager.close();

        return c;
    }

    @Override
    public Customer find(Customer obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Customer c = this.entityManager.find(Customer.class, obj.getId());

        this.entityManager.close();

        return c;
    }

    @Override
    public List<Customer> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT c FROM Customer c ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<Customer>) lst;
    }

    @Override
    public void update(Customer obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(Customer obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public boolean delete(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        Customer c = this.entityManager.find(Customer.class, id);
        if (c != null) this.entityManager.remove(c);

        this.entityManager.getTransaction().commit();
        this.entityManager.close();

        return c != null;
    }

    @Override
    public boolean delete(Customer obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();

        return true;
    }

    public Customer findByCPF(String cpf) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = "SELECT c FROM Customer c WHERE c.cpf = :cpf";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("cpf", cpf);
        List lst = qry.getResultList();
        this.entityManager.close();

        return lst.isEmpty() ? null : (Customer) lst.get(0);
    }

    public Customer findByEmail(String email) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = "SELECT c FROM Customer c WHERE c.email = :email";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("email", email);
        List lst = qry.getResultList();
        this.entityManager.close();

        return lst.isEmpty() ? null : (Customer) lst.get(0);
    }

    public List<Customer> findWithFilter(String filter) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        String jpql = " SELECT c "
                + " FROM Customer c"
                + " WHERE c.name LIKE :filter OR c.email LIKE :filter ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("filter", "%" + filter + "%");
        List lst = qry.getResultList();
        this.entityManager.close();

        return (List<Customer>) lst;
    }
}