package model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = 0;
    private String name;
    private String description;
    private String imgUrl;
    private LocalDateTime expiredDate;
    private float price;
    private int stock;
    private String sku;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    List<Promotion> promotions;

    @OneToMany(mappedBy = "product")
    List<ShoppingCart> shoppingCarts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    List<ProductSupplier> productSuppliers;

    @OneToMany(mappedBy = "product")
    List<ItemOrder> itemOrders;
}