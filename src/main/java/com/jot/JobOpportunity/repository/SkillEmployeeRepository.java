package com.jot.JobOpportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.SkillEmployee;

@Repository
public interface SkillEmployeeRepository extends JpaRepository<SkillEmployee, Long>{

	@Query(value = "SELECT se FROM SkillEmployee se WHERE se.employeeId = :employeeId")
	public List<SkillEmployee> getByEmployeeId(@Param("employeeId") Long employeeId);

	@Query(value = "SELECT se.skill FROM SkillEmployee se WHERE se.employeeId = :employeeId")
	public List<String> getNameByEmployeeId(@Param("employeeId") Long employeeId);

//	@Query(value = "SELECT se.skill FROM SkillEmployee se WHERE se.employeeId = :employeeId")
//	public List<String> getByEmployeeId(@Param("employeeId") Long employeeId);

	@Modifying
	@Query(value = "DELETE FROM SkillEmployee WHERE employeeId = :employeeId")
	public void deleteByEmployeeId(@Param("employeeId") Long employeeId);

}
