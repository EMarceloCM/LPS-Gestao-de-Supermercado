package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.Address;
import model.entities.Customer;
import repository.interfaces.IRepository;
import java.util.List;

public class AddressRepository implements IRepository<Address> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public AddressRepository() {}

    @Override
    public Address find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Address a = this.entityManager.find(Address.class, id);
        this.entityManager.clear();
        this.entityManager.close();

        return a;
    }

    @Override
    public Address find(Address obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Address a = this.entityManager.find(Address.class, obj.getId());

        this.entityManager.clear();
        this.entityManager.close();

        return a;
    }

    @Override
    public List<Address> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT a FROM Address a ";
        qry = this.entityManager.createQuery(jpql);
        List<Address> lst = qry.getResultList();

        this.entityManager.clear();
        this.entityManager.close();

        return lst;
    }

    @Override
    public void update(Address obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public void save(Address obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        Customer c = entityManager.find(Customer.class, obj.getCustomer().getId());
        if (c == null) {
            throw new IllegalArgumentException("ID do usuario não encontrado.");
        }
        obj.setCustomer(c);

        this.entityManager.persist(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public boolean delete(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        Address a = this.entityManager.find(Address.class, id);
        if (a != null) this.entityManager.remove(a);

        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return a != null;
    }

    @Override
    public boolean delete(Address obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return true;
    }

    public List<Address> findByCostumer(int customer_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = "SELECT a FROM Address a WHERE a.customer.id = :customer_id";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("customer_id", customer_id);
        List<Address> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return lst;
    }

    public List<Address> findWithFilter(String filter) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT a "
                + " FROM Address a"
                + " WHERE a.street LIKE :filter OR a.complement LIKE :filter OR a.neighborhood LIKE :filter ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("filter", "%" + filter + "%");
        List<Address> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return lst;
    }
}