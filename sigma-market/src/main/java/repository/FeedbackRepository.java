package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.Feedback;
import repository.interfaces.IRepository;
import java.util.List;

public class FeedbackRepository implements IRepository<Feedback> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public FeedbackRepository() {}

    @Override
    public Feedback find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Feedback f = this.entityManager.find(Feedback.class, id);
        this.entityManager.clear();
        this.entityManager.close();

        return f;
    }

    @Override
    public Feedback find(Feedback obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Feedback f = this.entityManager.find(Feedback.class, obj.getId());

        this.entityManager.clear();
        this.entityManager.close();

        return f;
    }

    @Override
    public List<Feedback> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT f FROM Feedback f ";
        qry = this.entityManager.createQuery(jpql);
        List<Feedback> lst = qry.getResultList();

        this.entityManager.clear();
        this.entityManager.close();

        return lst;
    }

    @Override
    public void update(Feedback obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public void save(Feedback obj) {
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

        Feedback f = this.entityManager.find(Feedback.class, id);
        if (f != null) this.entityManager.remove(f);

        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return f != null;
    }

    @Override
    public boolean delete(Feedback obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return true;
    }

    public List<Feedback> findByCustomer(int customerId) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT f FROM Feedback f WHERE f.customer.id = :customerId ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("customerId", customerId);
        List<Feedback> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return lst;
    }

    public Feedback findByOrder(int orderId) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT f FROM Feedback f WHERE f.order.id = :orderId ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("orderId", orderId);
        List<Feedback> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return lst.isEmpty() ? null : lst.getFirst();
    }

    public List<Feedback> findWithFilter(String filter) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        String jpql = " SELECT f "
                + " FROM Feedback f"
                + " WHERE f.review LIKE :filter ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("filter", "%" + filter + "%");
        List<Feedback> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return lst;
    }
}