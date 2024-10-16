package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductSupplier {
    private int id;
    private int product_id;
    private int supplier_id;
    private float purchasePrice;
}