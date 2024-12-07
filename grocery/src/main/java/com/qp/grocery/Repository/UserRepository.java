package com.qp.grocery.Repository;

import com.qp.grocery.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Optional<Users> findByName(String name);
}
