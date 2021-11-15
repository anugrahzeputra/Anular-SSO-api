package com.enigma.anularssoapi.repository;

import com.enigma.anularssoapi.entity.AnularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnularUserRepository extends JpaRepository<AnularUser, String> {
    @Query(value = "SELECT COUNT(*) FROM MST_USER", nativeQuery = true)
    Integer getUserId();
}
