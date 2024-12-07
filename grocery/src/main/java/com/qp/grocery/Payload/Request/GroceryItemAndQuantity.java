package com.qp.grocery.Payload.Request;

import lombok.Data;

import java.util.UUID;

@Data
public class GroceryItemAndQuantity {

    private UUID groceryItemId;

    private int quantity;

}
