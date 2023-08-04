package com.jot.JobOpportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.Wards;

@Repository
public interface WardsRepository extends JpaRepository<Wards, Long> {
	@Query(value = "SELECT s FROM Wards s WHERE s.districtCode = :code")
	public List<Wards> getWardByDistrictCode(@Param("code") String code);

	@Query(value = "SELECT s.fullName FROM Wards s WHERE s.code = :code")
	public String getName(@Param("code") String code);
}
