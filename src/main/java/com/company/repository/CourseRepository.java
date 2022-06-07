package com.company.repository;

import com.company.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByName(String name);

    @Transactional
    @Modifying
    @Query("update CourseEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update CourseEntity set name=:name, duration=:duration, price =:price" +
            " where id=:id")
    void updateCourseDetail(@Param("name") String name, @Param("duration") Integer duration,
                            @Param("price") Double price, @Param("id") Long id);
}