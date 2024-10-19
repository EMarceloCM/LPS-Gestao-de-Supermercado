package model;

import jakarta.persistence.*;
import lombok.*;
import model.enums.OrderStatus;
import model.enums.PaymentStatus;
import model.enums.PaymentType;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime date;
    private float totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="customer_id")
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

    @OneToMany(mappedBy = "order")
    List<ItemOrder> itemOrders;

    @OneToOne(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Feedback feedback;
}