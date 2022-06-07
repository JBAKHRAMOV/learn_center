package com.company.repository;

import com.company.entity.TimeTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface TimeTableRepository extends JpaRepository<TimeTableEntity, Long> {

    @Transactional
    @Modifying
    @Query("update TimeTableEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update TimeTableEntity set startTime=:startTime, endTime=:endTime where id=:id")
    void updateTimeTableDetail(@Param("startTime") String startTime, @Param("endTime") String endTime,
                               @Param("id") Long id);
}