package com.jot.JobOpportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.Districts;

@Repository
public interface DistrictsRepository extends JpaRepository<Districts, Long> {
	
	@Query(value = "SELECT s FROM Districts s WHERE s.provinceCode = :code")
	public List<Districts> getByProvinceCode(@Param("code") String code);

	@Query(value = "SELECT s.fullName FROM Districts s WHERE s.code = :code")
	public String getName(@Param("code") String code);
}
