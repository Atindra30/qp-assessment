package com.qp.grocery.Service;

import com.qp.grocery.Model.GroceryItem;
import com.qp.grocery.Model.GroceryItemInventoryLevel;
import com.qp.grocery.Model.UserGroceryBooking;
import com.qp.grocery.Payload.Request.GroceryItemAndQuantity;
import com.qp.grocery.Payload.Request.GroceryItemPayload;
import com.qp.grocery.Repository.GroceryItemInventoryLevelRepository;
import com.qp.grocery.Repository.GroceryItemRepository;
import com.qp.grocery.Repository.UserGroceryBookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class GroceryItemService {

    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private GroceryItemInventoryLevelRepository groceryItemInventoryLevelRepository;

    @Autowired
    private UserGroceryBookingRepository userGroceryBookingRepository;


    @Transactional
    public void addGroceryItemService(GroceryItemPayload requestPayload){

        GroceryItem groceryItem = new GroceryItem();
        groceryItem.setName(requestPayload.getName());
        groceryItem.setDescription(requestPayload.getDescription());
        groceryItem.setPrice(requestPayload.getPrice());
        groceryItem.getGroceryItemInventoryLevel().setQuantity(requestPayload.getQuantity());

        groceryItemRepository.save(groceryItem);
    }

    public List<GroceryItemPayload> getGroceryItemsService(){

        List<GroceryItem> groceryItems = groceryItemRepository.findAll();
        List<GroceryItemPayload> groceryItemPayloadList = groceryItems.stream().map(g -> {
            GroceryItemPayload groceryItemPayload = new GroceryItemPayload();
            groceryItemPayload.setId(g.getId());
            groceryItemPayload.setName(g.getName());
            groceryItemPayload.setDescription(g.getDescription());
            groceryItemPayload.setQuantity(g.getGroceryItemInventoryLevel().getQuantity());
            return groceryItemPayload;
        }).collect(Collectors.toList());

        return groceryItemPayloadList;

    }

    public void deleteGroceryItemService(UUID groceryItemId) throws Exception{
        GroceryItem groceryItem = groceryItemRepository.findById(groceryItemId)
                .orElseThrow(() -> new Exception("Grocery not found"));
        groceryItemRepository.delete(groceryItem);
    }

    public void updateGroceryItemService(GroceryItemPayload updatedGroceryItem) throws Exception {

        GroceryItem groceryItem = groceryItemRepository.findById(updatedGroceryItem.getId())
                .orElseThrow(() -> new Exception("Grocery not found"));

        groceryItem.setName(updatedGroceryItem.getName());
        groceryItem.setDescription(updatedGroceryItem.getDescription());
        groceryItem.setPrice(updatedGroceryItem.getPrice());
        groceryItem.getGroceryItemInventoryLevel().setQuantity(updatedGroceryItem.getQuantity());

        groceryItemRepository.save(groceryItem);
    }

    public void updateGroceryItemInventoryLevel(GroceryItemInventoryLevel updateInventoryLevel) throws Exception {
        GroceryItemInventoryLevel inventoryLevel = groceryItemInventoryLevelRepository.findByGroceryItemId(updateInventoryLevel.getGroceryItemId())
                .orElseThrow(() -> new Exception("Grocery Item not found"));

        inventoryLevel.setQuantity(updateInventoryLevel.getQuantity());

    }

    public List<GroceryItemPayload> getAvailableGroceryItemsService() throws Exception {
        List<GroceryItem> groceryItems = groceryItemRepository.findAllWithInventoryGreaterThanZero();
        if (groceryItems == null){
            throw new Exception("groceryItemPayloadList");
        }
        List<GroceryItemPayload> groceryItemPayloadList = groceryItems.stream().map(g -> {
            GroceryItemPayload groceryItemPayload = new GroceryItemPayload();
            groceryItemPayload.setId(g.getId());
            groceryItemPayload.setName(g.getName());
            groceryItemPayload.setDescription(g.getDescription());
            groceryItemPayload.setQuantity(g.getGroceryItemInventoryLevel().getQuantity());
            return groceryItemPayload;
        }).collect(Collectors.toList());


        return groceryItemPayloadList;
    }


    @Transactional
    public void bookGroceryItemsService(List<GroceryItemAndQuantity> groceryItemAndQuantityList, UUID userId) throws Exception {
        if (groceryItemAndQuantityList == null || groceryItemAndQuantityList.isEmpty()) {
            throw new IllegalArgumentException("Grocery item and quantity list cannot be null or empty.");
        }

        // Extract required grocery item IDs
        List<UUID> requiredGroceryItemsId = groceryItemAndQuantityList.stream()
                .map(GroceryItemAndQuantity::getGroceryItemId)
                .collect(Collectors.toList());

        // Fetch required grocery items from the database
        List<GroceryItem> requiredGroceryItems = groceryItemRepository.findByIdIn(requiredGroceryItemsId);

        // Construct a map of GroceryItem by their IDs
        Map<UUID, GroceryItem> groceryItemMap = requiredGroceryItems.stream()
                .collect(Collectors.toMap(GroceryItem::getId, item -> item));

        List<UserGroceryBooking> userGroceryBookingList = new ArrayList<>();

        // Process each GroceryItemAndQuantity
        for (GroceryItemAndQuantity groceryItemAndQuantity : groceryItemAndQuantityList) {
            GroceryItem groceryItem = groceryItemMap.get(groceryItemAndQuantity.getGroceryItemId());

            if (groceryItem == null) {
                throw new IllegalArgumentException("Grocery item with ID " + groceryItemAndQuantity.getGroceryItemId() + " not found.");
            }

            int remainingQuantity = groceryItem.getGroceryItemInventoryLevel().getQuantity() - groceryItemAndQuantity.getQuantity();

            if (remainingQuantity < 0) {
                throw new Exception(
                        groceryItem.getName() + " has insufficient quantity. Available: " +
                                groceryItem.getGroceryItemInventoryLevel().getQuantity()
                );
            }

            // Update the quantity
            groceryItem.getGroceryItemInventoryLevel().setQuantity(remainingQuantity);

            UserGroceryBooking userGroceryBooking = new UserGroceryBooking();
            userGroceryBooking.setUserId(userId);
            userGroceryBooking.setGroceryItemId(groceryItemAndQuantity.getGroceryItemId());
            userGroceryBooking.setCreated(LocalDate.now());

            userGroceryBookingList.add(userGroceryBooking);

        }

        // Save the updated grocery items
        groceryItemRepository.saveAll(groceryItemMap.values());
        userGroceryBookingRepository.saveAll(userGroceryBookingList);

    }
}
