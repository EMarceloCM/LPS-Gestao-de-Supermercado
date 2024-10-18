package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.Feedback;
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
        this.entityManager.close();

        return f;
    }

    @Override
    public Feedback find(Feedback obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Feedback f = this.entityManager.find(Feedback.class, obj.getId());

        this.entityManager.close();

        return f;
    }

    @Override
    public List<Feedback> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT f FROM Feedback f ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<Feedback>) lst;
    }

    @Override
    public void update(Feedback obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(Feedback obj) {
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

        Feedback f = this.entityManager.find(Feedback.class, id);
        if (f != null) this.entityManager.remove(f);

        this.entityManager.getTransaction().commit();
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

        return true;
    }
}