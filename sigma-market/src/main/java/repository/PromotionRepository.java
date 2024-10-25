package repository;

import factory.DatabaseJPA;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
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
        this.entityManager.close();

        if(verifyDate(p.getCreationDate(), p.getDurationMinutes())) {
            deactivatePromotion(p.getId());
            p.setActive(false);
        }

        return p;
    }

    @Override
    public Promotion find(Promotion obj) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        Promotion p = this.entityManager.find(Promotion.class, obj.getId());
        this.entityManager.close();

        if(verifyDate(p.getCreationDate(), p.getDurationMinutes())) {
            deactivatePromotion(p.getId());
            p.setActive(false);
        }

        return p;
    }

    @Override
    public List<Promotion> findAll() {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        jpql = " SELECT p FROM Promotion p ";
        qry = this.entityManager.createQuery(jpql);
        List<Promotion> lst = qry.getResultList();

        this.entityManager.close();

        for(int i = 0; i < lst.size(); i++) {
            if(verifyDate(lst.get(i).getCreationDate(), lst.get(i).getDurationMinutes())) {
                deactivatePromotion(lst.get(i).getId());
                lst.get(i).setActive(false);
            }
        }

        return lst;
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

    public List<Promotion> findByProduct(int product_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        jpql = " SELECT p FROM Promotion p WHERE p.product.id = :product_id";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("product_id", product_id);
        List<Promotion> lst = qry.getResultList();
        this.entityManager.close();

        for(int i = 0; i < lst.size(); i++) {
            if(verifyDate(lst.get(i).getCreationDate(), lst.get(i).getDurationMinutes())) {
                deactivatePromotion(lst.get(i).getId());
                lst.get(i).setActive(false);
            }
        }

        return lst.isEmpty() ? null : lst;
    }

    public List<Promotion> findActive(boolean isActive) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        String jpql = " SELECT p "
                + " FROM Promotion p "
                + " WHERE p.isActive = :isActive ";
        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("isActive", isActive);
        List<Promotion> lst = qry.getResultList();
        this.entityManager.close();

        for(int i = 0; i < lst.size(); i++) {
            if(verifyDate(lst.get(i).getCreationDate(), lst.get(i).getDurationMinutes())) {
                deactivatePromotion(lst.get(i).getId());
                lst.get(i).setActive(false);
            }
        }

        return lst;
    }

    public Promotion findActiveByProduct(int product_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();

        String jpql = " SELECT p FROM Promotion p "
                + " WHERE p.product.id = :product_id "
                + " AND p.isActive = true";

        qry = this.entityManager.createQuery(jpql);
        qry.setParameter("product_id", product_id);

        List<Promotion> lst = qry.getResultList();
        this.entityManager.close();

        if(verifyDate(lst.getFirst().getCreationDate(), lst.getFirst().getDurationMinutes())) {
            deactivatePromotion(lst.getFirst().getId());
            lst.getFirst().setActive(false);
        }

        return lst.isEmpty() ? null : lst.getFirst();
    }

    public void deactivatePromotion(int promotion_id) {
        this.entityManager = DatabaseJPA.getInstance().getEntityManager();
        this.entityManager.getTransaction().begin();

        Promotion promotion = this.entityManager.find(Promotion.class, promotion_id);

        if (promotion != null) {
            promotion.setActive(false);
            this.entityManager.merge(promotion);
        }

        this.entityManager.getTransaction().commit();
        this.entityManager.close();
    }

    private boolean verifyDate(LocalDateTime date, int duration) {
        LocalDateTime finalDate = date.plusMinutes(duration);
        return finalDate.isAfter(ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toLocalDateTime());
    }
}