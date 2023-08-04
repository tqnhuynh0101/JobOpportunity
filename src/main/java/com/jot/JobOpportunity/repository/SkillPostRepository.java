package com.jot.JobOpportunity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jot.JobOpportunity.entity.SkillPost;

@Repository
public interface SkillPostRepository extends JpaRepository<SkillPost, Long> {
	
	@Query(value = "SELECT sp FROM SkillPost sp WHERE sp.postId = :postId")
	public List<SkillPost> getByPostId(@Param("postId") Long postId);
	
}
