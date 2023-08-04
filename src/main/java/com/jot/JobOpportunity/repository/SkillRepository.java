package com.jot.JobOpportunity.repository;

import java.util.List;

import com.jot.JobOpportunity.dto.skill.SkillItfDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

	@Query(value = "SELECT s FROM Skill s WHERE type = 0")
	public List<Skill> getAllBaseSkill();

//	@Query(value = "SELECT s.skill FROM Skill s WHERE s.type = false")
//	public List<String> getAllBaseSkill();

	@Query(value = "SELECT s.id as id, s.skill as skill, s.type as type, s.create_by as createBy FROM Skill s",nativeQuery = true)
	public List<SkillItfDto> getAll();

	@Query(value = "SELECT * FROM Skill s WHERE s.id = :id", nativeQuery = true)
	public Skill getSkillById(@Param("id") Long id);

	@Modifying
    @Query(value =  "UPDATE Skill s SET s.type = false WHERE s.id = :id")
	public void approveSkillbyId(@Param("id") Long id);

	@Query(value = "SELECT s FROM Skill s WHERE UPPER(s.skill) = :skill")
    Skill getSkillBySkillName(@Param("skill") String skill);

}
