package com.enigma.anularssoapi.repository;

import com.enigma.anularssoapi.entity.AnularAccount;
import com.enigma.anularssoapi.entity.AnularGroup;
import com.enigma.anularssoapi.entity.AnularUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnularAccountRepository extends JpaRepository<AnularAccount, String> {

    @Query(value = "SELECT FROM mst_account WHERE agid = :group AND auid = :user", nativeQuery = true)
    Optional<AnularAccount> findUserAndGroup(
            @Param("user") String user,
            @Param("group") String group
    );
}
