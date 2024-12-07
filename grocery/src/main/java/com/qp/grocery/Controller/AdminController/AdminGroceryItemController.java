package com.qp.grocery.Controller.AdminController;

import com.qp.grocery.Model.GroceryItemInventoryLevel;
import com.qp.grocery.Payload.Request.GroceryItemPayload;
import com.qp.grocery.Payload.Response.ApiResponse;
import com.qp.grocery.Service.GroceryItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/admin")
public class AdminGroceryItemController {

    @Autowired
    private GroceryItemService groceryItemService;


    @PostMapping("/grocery/addGroceryItem")
    public ResponseEntity<ApiResponse> addGroceryItem(@RequestBody @Valid GroceryItemPayload requestPayload){
        groceryItemService.addGroceryItemService(requestPayload);
        return ResponseEntity.ok(new ApiResponse(true, "Grocery item added successfully"));
    }

    @GetMapping("/grocery/getGroceryItems")
    public ResponseEntity<ApiResponse> getGroceryItems(){
        List<GroceryItemPayload> groceryItems = groceryItemService.getGroceryItemsService();
        if (groceryItems == null){
            return ResponseEntity.ok(new ApiResponse(false, "No grocery item found"));

        }
        return ResponseEntity.ok(new ApiResponse(true, "Grocery items", groceryItems));
    }

    @DeleteMapping("/grocery/{groceryItemId}/deleteGroceryItem")
    public ResponseEntity<ApiResponse> deleteGroceryItem(@PathVariable UUID groceryItemId){
        try {
            groceryItemService.deleteGroceryItemService(groceryItemId);
            return ResponseEntity.ok(new ApiResponse(true, "Grocery item deleted"));
        } catch (Exception e) {
            log.error("Error deleting grocery item "+e);
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/grocery/updateGroceryItem")
    public ResponseEntity<ApiResponse> deleteGroceryItem(@RequestBody @Valid GroceryItemPayload requestPayload){
        try {
            groceryItemService.updateGroceryItemService(requestPayload);
            return ResponseEntity.ok(new ApiResponse(true, "Grocery item update successfully"));
        } catch (Exception e) {
            log.error("Error deleting grocery item "+e);
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }

    @PutMapping("/grocery/updateGroceryItemInventoryLevel")
    public ResponseEntity<ApiResponse> deleteGroceryItem(@RequestBody GroceryItemInventoryLevel requestPayload){
        try {
            groceryItemService.updateGroceryItemInventoryLevel(requestPayload);
            return ResponseEntity.ok(new ApiResponse(true, "Grocery item inventory level update successfully"));
        } catch (Exception e) {
            log.error("Error deleting grocery item "+e);
            return ResponseEntity.ok(new ApiResponse(false, e.getMessage()));
        }
    }


}
