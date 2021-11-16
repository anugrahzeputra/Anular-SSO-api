package com.enigma.anularssoapi.repository;

import com.enigma.anularssoapi.entity.AnularSiteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnularSiteTypeRepository extends JpaRepository<AnularSiteType, String> {
    @Query(value = "SELECT COUNT(*) FROM mst_site_type", nativeQuery = true)
    Integer getTypeId();

    Optional<AnularSiteType> findByNameType(String name);
}
