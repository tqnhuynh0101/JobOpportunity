package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.dto.payment.PaymentChartItfDto;
import com.jot.JobOpportunity.dto.payment.PaymentDetailItfDto;
import com.jot.JobOpportunity.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT" +
            "    YEAR AS year," +
            "    MONTH AS month," +
            "    SUM(amount) AS totalAmount " +
            "FROM " +
            "    payment WHERE year = :year" +
            " GROUP BY" +
            "    YEAR, MONTH " +
            "ORDER BY " +
            "    YEAR, MONTH;", nativeQuery = true)
    List<PaymentChartItfDto> getIncome(@Param("year") String year);


    @Query(value = "SELECT p.employee_id as employeeId, p.amount as amount, p.create_By as createBy, p.create_Time as createTime" +
            " FROM Payment p WHERE p.employee_Id = :id", nativeQuery = true)
    List<PaymentDetailItfDto> getDealDetaill(@Param("id") Long id);
}
