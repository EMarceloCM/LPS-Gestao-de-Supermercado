package model.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String cnpj;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    @ToString.Exclude
    List<ProductSupplier> productSuppliers;
}