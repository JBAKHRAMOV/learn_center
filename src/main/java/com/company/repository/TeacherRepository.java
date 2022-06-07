package com.company.repository;

import com.company.entity.TeacherEntity;
import com.company.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    TeacherEntity findByUser(UserEntity userEntity);

    @Transactional
    @Modifying
    @Query("update TeacherEntity set salary=:salary where user=:user")
    void updateTeacherSalary(@Param("salary") Double salary, @Param("user") UserEntity userEntity);

    @Transactional
    @Modifying
    @Query("update TeacherEntity set lastModifiedDate=:lastModifiedDate where user=:user")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("user") UserEntity userEntity);

}