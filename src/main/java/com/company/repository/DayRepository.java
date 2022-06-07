package com.company.repository;

import com.company.entity.DayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface DayRepository extends JpaRepository<DayEntity, Long> {
    Optional<DayEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update DayEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update DayEntity set name=:name" +
            " where id=:id")
    void updateDayDetail(@Param("name") String name,
                         @Param("id") Long id);
}