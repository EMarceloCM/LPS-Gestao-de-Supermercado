package model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String review;
    private LocalDateTime date;
    private int stars;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="order_id", unique = true)
    private Order order;
}