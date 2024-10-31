package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.Product;
import repository.interfaces.IRepository;
import java.util.List;

public class ProductRepository implements IRepository<Product> {

    private EntityManager entityManager;
    private Query qry;
    private String jpql;

    public ProductRepository() {}

    @Override
    public Product find(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Product p = this.entityManager.find(Product.class, id);
        this.entityManager.clear();
        this.entityManager.close();

        return p;
    }

    @Override
    public Product find(Product obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        Product p = this.entityManager.find(Product.class, obj.getId());

        this.entityManager.clear();
        this.entityManager.close();

        return p;
    }

    @Override
    public List<Product> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT p FROM Product p ";
        qry = this.entityManager.createQuery(jpql);
        List lst = qry.getResultList();

        this.entityManager.clear();
        this.entityManager.close();

        return (List<Product>) lst;
    }

    @Override
    public void update(Product obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public void save(Product obj) {
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

        Product p = this.entityManager.find(Product.class, id);
        if (p != null) {
            this.entityManager.remove(p);
        }

        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return p != null;
    }

    @Override
    public boolean delete(Product obj) {
        if (obj == null) return false;

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();

        return true;
    }

    public List<Product> findWithFilter(String filter) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        String jpql = " SELECT p "
                + " FROM Product p "
                + " WHERE p.name LIKE :filter OR p.description LIKE :filter ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("filter", "%" + filter + "%");
        List lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return (List<Product>) lst;
    }
}