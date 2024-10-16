package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Feedback {
    private int id;
    private int costumer_id;
    private int order_id;
    private String review;
}