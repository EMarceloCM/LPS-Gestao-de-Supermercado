package model.entities;

import jakarta.persistence.*;
import lombok.*;
import model.enums.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String password;
    private String cpf;
    private String email;
    private int profileId;

    @Enumerated(EnumType.STRING)
    private Role Role;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    List<Address> addresses;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    List<Feedback> feedbacks;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    List<Order> orders;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<ShoppingCart> shoppingCarts;
}