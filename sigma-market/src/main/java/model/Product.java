package model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
    private int sku;

    @OneToMany(mappedBy = "product")
    List<Promotion> promotions;

    @OneToMany(mappedBy = "product")
    List<ShoppingCart> shoppingCarts;
}