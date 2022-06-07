package com.company.repository;

import com.company.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    Optional<GroupEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update GroupEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate, @Param("id") Long id);

}