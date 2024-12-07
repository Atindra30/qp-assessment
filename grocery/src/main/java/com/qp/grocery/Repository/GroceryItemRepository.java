package com.qp.grocery.Repository;

import com.qp.grocery.Model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, UUID> {


    List<GroceryItem> findByIdIn(List<UUID> groceryItemId);

    @Query("SELECT g FROM GroceryItem g WHERE g.groceryItemInventoryLevel.quantity > 0")
    List<GroceryItem> findAllWithInventoryGreaterThanZero();

}
