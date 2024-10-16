package model;

import lombok.*;
import model.enums.Role;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Customer {
    private int id;
    private String name;
    private String password;
    private String cpf;
    private String email;
    private Role Role;
}