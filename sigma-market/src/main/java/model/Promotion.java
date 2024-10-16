package model;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Promotion {
    private int id;
    private int product_id;
    private float discountPercentage;
    private LocalDateTime creationDate;
    private int durationMinutes;
    private boolean isActive;
}