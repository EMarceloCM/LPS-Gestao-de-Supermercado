package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.ShoppingCart;
import repository.interfaces.IRepository;
import java.util.List;

public class ShoppingCartRepository implements IRepository<ShoppingCart> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public ShoppingCartRepository() {}

    @Override
    public ShoppingCart find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        ShoppingCart sc = this.entityManager.find(ShoppingCart.class, id);
        this.entityManager.clear();
        this.entityManager.close();

        return sc;
    }

    @Override
    public ShoppingCart find(ShoppingCart obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        ShoppingCart sc = this.entityManager.find(ShoppingCart.class, obj.getId());

        this.entityManager.clear();
        this.entityManager.close();

        return sc;
    }

    @Override
    public List<ShoppingCart> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT sc FROM ShoppingCart sc ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.clear();
        this.entityManager.close();

        return (List<ShoppingCart>) lst;
    }

    @Override
    public void update(ShoppingCart obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public void save(ShoppingCart obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public boolean delete(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        ShoppingCart sc = this.entityManager.find(ShoppingCart.class, id);
        if (sc != null) this.entityManager.remove(sc);

        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return sc != null;
    }

    @Override
    public boolean delete(ShoppingCart obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return true;
    }

    public List<ShoppingCart> findByCustomer(int customerId) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT sc FROM ShoppingCart sc WHERE sc.customer.id = :customerId ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("customerId", customerId);
        List lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return (List<ShoppingCart>) lst;
    }
}