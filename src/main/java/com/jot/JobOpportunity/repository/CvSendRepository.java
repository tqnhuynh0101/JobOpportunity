package com.jot.JobOpportunity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.CvSend;


@Repository
public interface CvSendRepository extends JpaRepository<CvSend, Long> {
	@Modifying
	@Query(value = "DELETE FROM CV_SEND cs WHERE uuid = :uuid", nativeQuery = true)
	public void deleteByUuid(@Param("uuid") String uuid);

	@Query(value = "SELECT cv FROM CvSend cv WHERE cv.uuid = :uuid")
	public CvSend getCvSendByUuid(@Param("uuid") String uuid);
}
