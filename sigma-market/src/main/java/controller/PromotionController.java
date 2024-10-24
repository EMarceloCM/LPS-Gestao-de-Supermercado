package controller;

import controller.tableModel.TMProduct;
import model.entities.Product;
import model.entities.Promotion;
import model.validations.ValidatePromotion;
import repository.PromotionRepository;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class PromotionController {
    private PromotionRepository repository;

    PromotionController() { repository = new PromotionRepository(); }

//    public void refreshTable(JTable t) {
//        List list = repository.findAll();
//        TMProduct model = new TMProduct(list);
//        t.setModel(model);
//    }
//
//    public void createPromotion(String discountPercentage, String creationDate, String durationMinutes, boolean isActive, Product product) {
//        Promotion p = ValidatePromotion.Validate(discountPercentage, creationDate, durationMinutes, isActive, product);
//        repository.save(p);
//    }
//
//    public void updatePromotion(int id, String discountPercentage, String creationDate, String durationMinutes, boolean isActive, Product product) {
//        Promotion p = ValidatePromotion.Validate(discountPercentage, creationDate, durationMinutes, isActive, product);
//        p.setId(id);
//        repository.update(p);
//    }
//
//    public void deletePromotion(int id) {
//        repository.delete(id);
//    }
//
//    public void filterTableByActive(JTable t, boolean isActive) {
//        List<Promotion> list = repository.findActive(isActive);
//        TMProduct model = new TMProduct(list);
//        t.setModel(model);
//    }
//
//    public void filterTableByProduct(JTable t, int product_id) {
//        List<Promotion> list = repository.findByProduct(product_id);
//        TMProduct model = new TMProduct(list);
//        t.setModel(model);
//    }
//
//    public void filterTableByActiveAndByProduct(JTable t, int product_id) {
//        Promotion p = repository.findActiveByProduct(product_id);
//        List<Promotion> lst = new ArrayList<>();
//        lst.add(p);
//
//        TMProduct model = new TMProduct(lst);
//        t.setModel(model);
//    }
}