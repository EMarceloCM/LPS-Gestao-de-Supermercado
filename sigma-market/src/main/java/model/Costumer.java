package model;

import jakarta.persistence.*;
import lombok.*;
import model.enums.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Costumer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String cpf;
    private String email;
    private Role Role;

    @OneToMany(mappedBy = "costumer")
    List<Address> addresses;

    @OneToMany(mappedBy = "costumer")
    List<Feedback> feedbacks;

    @OneToMany(mappedBy = "costumer")
    List<Order> orders;

    @OneToMany(mappedBy = "costumer", cascade = CascadeType.ALL)
    List<ShoppingCart> shoppingCarts;
}