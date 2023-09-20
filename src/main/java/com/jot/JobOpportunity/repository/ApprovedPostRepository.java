package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.entity.ApprovedPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovedPostRepository extends JpaRepository<ApprovedPost, Long> {

    @Query(value = "SELECT a FROM ApprovedPost a WHERE a.posterId = :posterId")
    ApprovedPost getByIdPosterId(@Param("posterId") Long posterId);
}
