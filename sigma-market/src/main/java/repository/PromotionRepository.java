package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import model.entities.Product;
import model.entities.Promotion;
import repository.interfaces.IRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
        this.entityManager.clear();
        this.entityManager.close();
        return p;
    }

    @Override
    public Promotion find(Promotion obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Promotion p = this.entityManager.find(Promotion.class, obj.getId());
        this.entityManager.clear();
        this.entityManager.close();
        return p;
    }

    @Override
    public List<Promotion> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT p FROM Promotion p ";
        qry = this.entityManager.createQuery(jpql);
        List<Promotion> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();
        return lst;
    }

    @Override
    public void update(Promotion obj) {
        deactivatePreviewPromotion(obj);

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        Promotion p = entityManager.find(Promotion.class, obj.getId());
        if (p == null) throw new IllegalArgumentException("Promoção com o ID fornecido não encontrada.");

        Product product = entityManager.find(Product.class, obj.getProduct().getId());
        if (product == null) throw new IllegalArgumentException("Produto com o ID fornecido não encontrado.");

        p.setDiscountPercentage(obj.getDiscountPercentage());
        p.setCreationDate(obj.getCreationDate());
        p.setDurationMinutes(obj.getDurationMinutes());
        p.setActive(obj.getActive());
        p.setProduct(product);

        // this.entityManager.merge(p); nao precisa porque a propr. já está sendo monitorada
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public void save(Promotion obj) {
        deactivatePreviewPromotion(obj);

        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        Product product = entityManager.find(Product.class, obj.getProduct().getId());
        if (product == null) {
            throw new IllegalArgumentException("Produto com o ID fornecido não encontrado.");
        }
        obj.setProduct(product);

        this.entityManager.persist(obj);
        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
        this.entityManager.close();
    }

    @Override
    public boolean delete(int id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        Promotion p = this.entityManager.find(Promotion.class, id);
        if (p != null)this.entityManager.remove(p);

        this.entityManager.getTransaction().commit();
        this.entityManager.clear();
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
        this.entityManager.clear();
        this.entityManager.close();

        return true;
    }

    public List<Promotion> findByProduct(int product_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT p FROM Promotion p WHERE p.product.id = :product_id";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("product_id", product_id);
        List<Promotion> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return lst.isEmpty() ? null : lst;
    }

    public List<Promotion> findActive(int isActive) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        String jpql = " SELECT p "
                + " FROM Promotion p "
                + " WHERE p.active = :isActive ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("isActive", isActive);
        List<Promotion> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        return lst;
    }

    public Promotion findActiveByProduct(int product_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        String jpql = " SELECT p FROM Promotion p "
                + " WHERE p.product.id = :product_id "
                + " AND p.active = 1";

        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("product_id", product_id);

        List<Promotion> lst = qry.getResultList();
        this.entityManager.clear();
        this.entityManager.close();

        if (lst == null || lst.isEmpty())
            return null;

        return lst.getFirst();
    }

    private void deactivatePreviewPromotion(Promotion promotion) {
        Promotion p = findActiveByProduct(promotion.getProduct().getId());
        if(p == null || p.getId() == promotion.getId()) return;
        p.setActive(0);
        update(p);
    }
}