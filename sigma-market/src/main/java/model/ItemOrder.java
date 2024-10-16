package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ItemOrder {
    private int id;
    private int product_id;
    private int order_id;
    private int quantity;
    private float totalAmount;
}