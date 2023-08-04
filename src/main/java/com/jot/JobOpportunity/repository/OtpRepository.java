package com.jot.JobOpportunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.Otp;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> {

	@Query(value = "SELECT otp FROM Otp otp WHERE otp.accountId = :accountId AND otp.otp = :otp")
	Otp getOtpByAccountIdAndOtp(@Param("accountId") Long accountId, @Param("otp") String otp);

	@Modifying
	@Query(value = "DELETE FROM Otp otp where otp.accountId = :accountId")
	void deleteByAccountId(@Param("accountId") Long accountId);
}
