package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.Costumer;
import model.Supplier;
import repository.interfaces.IRepository;
import java.util.List;

public class CostumerRepository implements IRepository<Costumer> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    @Override
    public Costumer find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Costumer c = this.entityManager.find(Costumer.class, id);
        this.entityManager.close();

        return c;
    }

    @Override
    public Costumer find(Costumer obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Costumer c = this.entityManager.find(Costumer.class, obj.getId());

        this.entityManager.close();

        return c;
    }

    @Override
    public List<Costumer> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT c FROM Costumer c ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<Costumer>) lst;
    }

    @Override
    public void update(Costumer obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(Costumer obj) {
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

        Supplier supplier = this.entityManager.find(Supplier.class, id);
        if (supplier != null) this.entityManager.remove(supplier);

        this.entityManager.getTransaction().commit();
        this.entityManager.close();

        return supplier != null;
    }

    @Override
    public boolean delete(Costumer obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();

        return true;
    }
}