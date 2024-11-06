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


    public String getStarsAsEmote() {
            StringBuilder str = new StringBuilder();
            for (int i = 0; i < stars; i++) {
                str.append("★");
            }
            for (int i = stars; i < 5; i++) {
                str.append("☆");
            }
            return str.toString();
    }
}