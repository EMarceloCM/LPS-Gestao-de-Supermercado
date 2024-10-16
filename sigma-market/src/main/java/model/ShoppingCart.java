package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ShoppingCart {
    private int id;
    private int product_id;
    private int costumer_id;
    private int quantity;
    private float totalAmount;
}