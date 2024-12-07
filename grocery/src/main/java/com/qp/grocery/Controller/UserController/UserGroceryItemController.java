package com.qp.grocery.Controller.UserController;

import com.qp.grocery.Payload.Request.GroceryItemAndQuantity;
import com.qp.grocery.Payload.Request.GroceryItemPayload;
import com.qp.grocery.Payload.Response.ApiResponse;
import com.qp.grocery.Service.GroceryItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserGroceryItemController {

    @Autowired
    private GroceryItemService groceryItemService;


    @GetMapping("/grocery/getAvailableGroceryItems")
    public ResponseEntity<ApiResponse> getGroceryItems(){
        try {
            List<GroceryItemPayload> groceryItems = groceryItemService.getAvailableGroceryItemsService();
            return ResponseEntity.ok(new ApiResponse(true, "Available Grocery items", groceryItems));
        } catch (Exception e) {
            log.error("Error fetching available grocery items");
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @PostMapping("/user/{userId}/grocery/bookGroceryItems")
    public ResponseEntity<ApiResponse> bookGroceryItems(@RequestBody List<GroceryItemAndQuantity> requestPayload, @PathVariable UUID userId){
        try {
            groceryItemService.bookGroceryItemsService(requestPayload, userId);
            return ResponseEntity.ok(new ApiResponse(true, "Grocery items booked successfully"));
        } catch (Exception e) {
            log.error("Error Booking Grocery items "+e);
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }
}
