package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.Promotion;
import repository.interfaces.IRepository;
import java.util.List;

public class PromotionRepository implements IRepository<Promotion> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public PromotionRepository() {}

    @Override
    public Promotion find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Promotion p = this.entityManager.find(Promotion.class, id);
        this.entityManager.close();

        return p;
    }

    @Override
    public Promotion find(Promotion obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Promotion p = this.entityManager.find(Promotion.class, obj.getId());

        this.entityManager.close();

        return p;
    }

    @Override
    public List<Promotion> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT p FROM Promotion p ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<Promotion>) lst;
    }

    @Override
    public void update(Promotion obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(Promotion obj) {
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

        Promotion p = this.entityManager.find(Promotion.class, id);
        if (p != null) this.entityManager.remove(p);

        this.entityManager.getTransaction().commit();
        this.entityManager.close();

        return p != null;
    }

    @Override
    public boolean delete(Promotion obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();

        return true;
    }

    public Promotion findByProduct(int product_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT p FROM Promotion p WHERE p.product.id = :product_id";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("product_id", product_id);
        List lst = qry.getResultList();
        this.entityManager.close();

        return (Promotion) lst.get(0);
    }
}