package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.ItemOrder;
import repository.interfaces.IRepository;
import java.util.List;

public class ItemOrderRepository implements IRepository<ItemOrder> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public ItemOrderRepository() {}

    @Override
    public ItemOrder find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        ItemOrder i = this.entityManager.find(ItemOrder.class, id);
        this.entityManager.close();

        return i;
    }

    @Override
    public ItemOrder find(ItemOrder obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        ItemOrder i = this.entityManager.find(ItemOrder.class, obj.getId());

        this.entityManager.close();

        return i;
    }

    @Override
    public List<ItemOrder> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT i FROM ItemOrder i ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<ItemOrder>) lst;
    }

    @Override
    public void update(ItemOrder obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(ItemOrder obj) {
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

        ItemOrder i = this.entityManager.find(ItemOrder.class, id);
        if (i != null) this.entityManager.remove(i);

        this.entityManager.getTransaction().commit();
        this.entityManager.close();

        return i != null;
    }

    @Override
    public boolean delete(ItemOrder obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();

        return true;
    }

    public List<ItemOrder> findByOrder(int order_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT i FROM ItemOrder i WHERE i.order_id = :order_id ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("order_id", order_id);
        List lst = qry.getResultList();
        this.entityManager.close();

        return (List<ItemOrder>) lst;
    }
}