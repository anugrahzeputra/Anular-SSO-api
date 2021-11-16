package com.enigma.anularssoapi.repository;

import com.enigma.anularssoapi.entity.AnularGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnularGroupRepository extends JpaRepository<AnularGroup, String> {
    @Query(value = "SELECT COUNT(*) FROM mst_group", nativeQuery = true)
    Integer getGroupId();

    @Query(value = "SELECT * FROM mst_group WHERE id LIKE '?1%' AND id LIKE '%?2%'", nativeQuery = true)
    Optional<AnularGroup> getGroupIdALIke(@Param("?1") String arg1, @Param("?2") String arg2);
}
