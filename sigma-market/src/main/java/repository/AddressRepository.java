package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.Address;
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
        this.entityManager.close();

        return a;
    }

    @Override
    public Address find(Address obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Address a = this.entityManager.find(Address.class, obj.getId());

        this.entityManager.close();

        return a;
    }

    @Override
    public List<Address> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT a FROM Address a ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<Address>) lst;
    }

    @Override
    public void update(Address obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(Address obj) {
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

        Address a = this.entityManager.find(Address.class, id);
        if (a != null) this.entityManager.remove(a);

        this.entityManager.getTransaction().commit();
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

        return true;
    }
}