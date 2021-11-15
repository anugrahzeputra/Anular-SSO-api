package com.enigma.anularssoapi.repository;

import com.enigma.anularssoapi.entity.AnularSiteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnularSiteTypeRepository extends JpaRepository<AnularSiteType, String> {
    @Query(value = "SELECT COUNT(*) FROM MST_SITE_TYPE", nativeQuery = true)
    Integer getTypeId();
}
