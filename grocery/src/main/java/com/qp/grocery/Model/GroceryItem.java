package com.qp.grocery.Model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
public class GroceryItem {

    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID id;

    private String name;

    private String description;

    private BigDecimal price;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="groceryItemId")
    private GroceryItemInventoryLevel groceryItemInventoryLevel;


}
