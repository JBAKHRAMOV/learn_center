package com.company.repository;

import com.company.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {

    @Transactional
    @Modifying
    @Query("update PaymentEntity p set p.studentId=:studentId, p.paymentTypeId=:paymentTypeId," +
            "p.sum=:sum where p.id=:id")
    void updatePaymentDetail(@Param("studentId") Long studentId,
                             @Param("paymentTypeId") Long paymentTypeId,
                             @Param("sum") Double sum,
                             @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update PaymentEntity set lastModifiedDate=:lastModifiedDate where id=:id")
    void updateLastModifiedDate(@Param("lastModifiedDate") LocalDateTime lastModifiedDate,
                                @Param("id") Long id);
}