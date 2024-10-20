package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.ProductSupplier;
import repository.interfaces.IRepository;
import java.util.List;

public class ProductSupplierRepository implements IRepository<ProductSupplier> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public ProductSupplierRepository() {}

    @Override
    public ProductSupplier find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        ProductSupplier ps = this.entityManager.find(ProductSupplier.class, id);
        this.entityManager.close();

        return ps;
    }

    @Override
    public ProductSupplier find(ProductSupplier obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        ProductSupplier ps = this.entityManager.find(ProductSupplier.class, obj.getId());

        this.entityManager.close();

        return ps;
    }

    @Override
    public List<ProductSupplier> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT ps FROM ProductSupplier ps ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.close();

        return (List<ProductSupplier>) lst;
    }

    @Override
    public void update(ProductSupplier obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    @Override
    public void save(ProductSupplier obj) {
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

        ProductSupplier ps = this.entityManager.find(ProductSupplier.class, id);
        if (ps != null) this.entityManager.remove(ps);

        this.entityManager.getTransaction().commit();
        this.entityManager.close();

        return ps != null;
    }

    @Override
    public boolean delete(ProductSupplier obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();

        return true;
    }
}