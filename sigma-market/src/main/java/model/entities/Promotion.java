package model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private float discountPercentage;
    private LocalDateTime creationDate;
    private int durationMinutes;
    private int active;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="product_id")
    private Product product;

    public float getFinalPrice() {
        return (1 - (this.getDiscountPercentage()/100)) * product.getPrice();
    }
}