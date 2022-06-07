package com.company.repository;

import com.company.entity.StudentEntity;
import com.company.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    StudentEntity findByUser(UserEntity userEntity);
    @Transactional
    @Modifying
    @Query("update StudentEntity set lastModifiedDate=:lastModifiedDate where user=:user")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("user") UserEntity userEntity);
}