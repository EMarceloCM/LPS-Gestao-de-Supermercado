package controller;

import controller.tableModel.TMPromotion;
import model.entities.Product;
import model.entities.Promotion;
import model.validations.ValidatePromotion;
import repository.PromotionRepository;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionController {
    private PromotionRepository repository;

    public PromotionController() { repository = new PromotionRepository(); }

    public void refreshTable(JTable t) {
        List<Promotion> list = repository.findAll();
        TMPromotion model = new TMPromotion(list);
        t.setModel(model);
    }

    public void createPromotion(String discountPercentage, String durationMinutes, boolean isActive, Product product) {
        Promotion p = ValidatePromotion.Validate(discountPercentage, durationMinutes, isActive, product);

        repository.save(p);
    }

    public void updatePromotion(int id, String discountPercentage, String durationMinutes, boolean isActive, Product product) {
        Promotion p = ValidatePromotion.Validate(discountPercentage, durationMinutes, isActive, product);
        p.setId(id);

        repository.update(p);
    }

    public void deletePromotion(int id) {
        repository.delete(id);
    }

    public void filterTableByActive(JTable t, boolean isActive) {
        List<Promotion> list = repository.findActive(isActive);
        TMPromotion model = new TMPromotion(list);
        t.setModel(model);
    }

    public void filterTableByProduct(JTable t, int product_id) {
        List<Promotion> list = repository.findByProduct(product_id);
        TMPromotion model = new TMPromotion(list);
        t.setModel(model);
    }

    public void filterTableByActiveAndByProduct(JTable t, int product_id) {
        Promotion p = repository.findActiveByProduct(product_id);
        List<Promotion> lst = new ArrayList<>();
        lst.add(p);

        TMPromotion model = new TMPromotion(lst);
        t.setModel(model);
    }

    private void verifyActivatedPromotions(int product_id) {
        Promotion p2 = repository.findActiveByProduct(product_id);
        if(p2 != null)
            repository.deactivatePromotion(p2.getId());
    }
}