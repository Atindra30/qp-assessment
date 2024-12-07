package com.qp.grocery.Payload.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class GroceryItemPayload {


    private UUID id;

    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    private int quantity;



}
