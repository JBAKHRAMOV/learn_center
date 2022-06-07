package com.company.repository;

import com.company.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    Optional<RoomEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update RoomEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update RoomEntity set name=:name, capacity=:capacity" +
            " where id=:id")
    void updateRoomDetail(@Param("name") String name,
                            @Param("capacity") Integer capacity, @Param("id") Long id);
}