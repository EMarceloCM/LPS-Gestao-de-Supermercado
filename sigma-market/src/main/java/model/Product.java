package model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Product {
    private int id = 0;
    private String name;
    private String description;
    private String imgUrl;
    private LocalDateTime expiredDate;
    private float price;
    private int stock;
    private int sku;
}