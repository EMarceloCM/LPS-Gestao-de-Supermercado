package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import model.entities.Supplier;
import jakarta.persistence.Query;
import repository.interfaces.IRepository;
import java.util.List;

public class SupplierRepository implements IRepository<Supplier> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public SupplierRepository () {}

    @Override
    public Supplier find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Supplier s = this.entityManager.find(Supplier.class, id);
        this.entityManager.clear();
        this.entityManager.close();

        return s;
    }

    @Override
    public Supplier find(Supplier obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Supplier s = this.entityManager.find(Supplier.class, obj.getId());

        this.entityManager.clear();
        this.entityManager.close();

        return s;
    }

    @Override
    public List<Supplier> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT s FROM Supplier s ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.clear();
        this.entityManager.close();

        return (List<Supplier>) lst;
    }

    @Override
    public void update(Supplier obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public void save(Supplier obj) {
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

        Supplier supplier = this.entityManager.find(Supplier.class, id);
        if (supplier != null) this.entityManager.remove(supplier);

        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return supplier != null;
    }

    @Override
    public boolean delete(Supplier obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return true;
    }

    public Supplier findByCNPJ(String cnpj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT s "
                + " FROM Supplier s "
                + " WHERE s.cnpj like :cnpj ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("cnpj", cnpj);
        List lst = qry.getResultList();

        this.entityManager.clear();
        this.entityManager.close();

        return lst.isEmpty() ? null : (Supplier) lst.getFirst();
    }

    public List<Supplier> findWithFilter(String filter) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        String jpql = " SELECT s "
                + " FROM Supplier s "
                + " WHERE s.cnpj LIKE :filter OR s.name LIKE :filter ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("filter", "%" + filter + "%");
        List lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return (List<Supplier>) lst;
    }
}