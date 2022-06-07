package com.company.repository;

import com.company.entity.ConfirmationSmsEntity;
import com.company.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationSmsRepository extends JpaRepository<ConfirmationSmsEntity, Long> {
    Optional<ConfirmationSmsEntity> findBySms(String sms);

    void deleteByUser(UserEntity userEntity);

    @Transactional
    @Modifying
    @Query("update ConfirmationSmsEntity set confirmedAt=:confirmedAt where sms=:sms")
    void updateConfirmedAt(@Param("confirmedAt") LocalDateTime confirmedAt, @Param("sms") String sms);
}