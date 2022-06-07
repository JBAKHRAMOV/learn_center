package com.company.repository;

import com.company.entity.GroupTimeTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface GroupTimeTableRepository extends JpaRepository<GroupTimeTableEntity, Long> {
    @Transactional
    @Modifying
    @Query("update GroupTimeTableEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update GroupTimeTableEntity set groupId=:groupId, timeTableId=:timeTableId" +
            " where id=:id")
    void updateGroupTimeTableDetail(@Param("groupId") Long groupId,
                                    @Param("timeTableId") Long timeTableId,
                                    @Param("id") Long id);
}