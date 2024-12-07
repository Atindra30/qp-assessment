package com.qp.grocery.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class UserGroceryBooking {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private UUID userId;

    private UUID groceryItemId;

    private LocalDate created;

}
