package com.company.repository;

import com.company.entity.GroupStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface GroupStudentRepository extends JpaRepository<GroupStudentEntity, Long> {
    @Transactional
    @Modifying
    @Query("update GroupStudentEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update GroupStudentEntity set groupId=:groupId, studentId=:studentId" +
            " where id=:id")
    void updateGroupTimeTableDetail(@Param("groupId") Long groupId,
                                    @Param("studentId") Long studentId,
                                    @Param("id") Long id);
}