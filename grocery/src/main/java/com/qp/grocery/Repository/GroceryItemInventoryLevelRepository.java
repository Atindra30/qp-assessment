package com.qp.grocery.Repository;

import com.qp.grocery.Model.GroceryItemInventoryLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroceryItemInventoryLevelRepository extends JpaRepository<GroceryItemInventoryLevel, Long> {

    Optional<GroceryItemInventoryLevel> findByGroceryItemId(UUID groceryItemId);
}
