package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.Order;
import repository.interfaces.IRepository;
import java.util.List;

public class OrderRepository implements IRepository<Order> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public OrderRepository() {}

    @Override
    public Order find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Order o = this.entityManager.find(Order.class, id);
        this.entityManager.close();

        return o;
    }

    @Override
    public Order find(Order obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Order o = this.entityManager.find(Order.class, obj.getId());

        this.entityManager.close();

        return o;
    }

    @Override
    public List<Order> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT o FROM Order o ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<Order>) lst;
    }

    @Override
    public void update(Order obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(Order obj) {
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

        Order o = this.entityManager.find(Order.class, id);
        if (o != null) this.entityManager.remove(o);

        this.entityManager.getTransaction().commit();
        this.entityManager.close();

        return o != null;
    }

    @Override
    public boolean delete(Order obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();

        return true;
    }
}