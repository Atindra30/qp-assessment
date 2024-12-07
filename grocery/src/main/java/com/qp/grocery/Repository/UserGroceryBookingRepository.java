package com.qp.grocery.Repository;

import com.qp.grocery.Model.UserGroceryBooking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroceryBookingRepository extends JpaRepository<UserGroceryBooking, Long> {
}
