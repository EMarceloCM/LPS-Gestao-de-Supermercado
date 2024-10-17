package model;

import jakarta.persistence.*;
import lombok.*;
import model.enums.OrderStatus;
import model.enums.PaymentStatus;
import model.enums.PaymentType;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private float totalAmount;
    private OrderStatus orderStatus;
    private PaymentStatus paymentStatus;
    private PaymentType paymentType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="costumer_id")
    private Costumer costumer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    // @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // private Feedback feedback; //acho que não precisa nesse caso pq é 1 pra 1
}