package com.company.repository;

import com.company.entity.RoomEntity;
import com.company.entity.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    Optional<StatusEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update StatusEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update StatusEntity set name=:name, description=:description" +
            " where id=:id")
    void updateStatusDetail(@Param("name") String name,
                          @Param("description") String description, @Param("id") Long id);
}