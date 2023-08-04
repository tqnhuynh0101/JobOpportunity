package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.entity.EmployeePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeePaymentRepository extends JpaRepository<EmployeePayment, Long> {
}
