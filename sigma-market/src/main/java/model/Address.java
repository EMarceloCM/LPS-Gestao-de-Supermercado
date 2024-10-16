package model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    private int id;
    private int costumer_id;
    private String street;
    private int number;
    private String complement;
    private String neighborhood;
}