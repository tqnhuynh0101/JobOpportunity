package com.jot.JobOpportunity.repository;

import com.jot.JobOpportunity.dto.applypost.ApplyPostManagementItfDto;
import com.jot.JobOpportunity.dto.applypost.MyApplyPostItfDto;
import com.jot.JobOpportunity.entity.ApplyPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplyPostRepository extends JpaRepository<ApplyPost, Long> {
    @Query(value = "SELECT a FROM ApplyPost a WHERE a.id = :id")
    ApplyPost getApplyPostById(@Param("id") Long id);

    @Query(value = "SELECT a FROM ApplyPost a WHERE a.accountId = :accountId AND a.posterId = :posterId")
    ApplyPost getApplyPostByAccountIdAndPosterId(@Param("accountId") Long accountId,@Param("posterId") Long posterId);

    @Query(value = "SELECT a FROM ApplyPost a WHERE a.accountId = :accountId AND a.postId = :postId")
    ApplyPost getApplyPostByAccountIdAndPostId(@Param("accountId") Long accountId,@Param("postId") Long postId);

    @Query(value = "SELECT a.id as id, a.poster_id as posterId, a.create_by as createBy, a.create_time as createTime, \n" +
            "            a.link_cv as linkCv, a.post_id as postId, a.type as type, " +
            "a.message as message, a.app_time as appTime FROM apply_post as a WHERE a.poster_id = :posterId ORDER BY a.id DESC", nativeQuery = true)
    List<ApplyPostManagementItfDto> getByPosterId(@Param("posterId") Long posterId);

    @Query(value = "SELECT a FROM ApplyPost a WHERE a.accountId = :accountId ORDER BY a.postId ASC")
    List<ApplyPost> getByAccountId(@Param("accountId") Long accountId);

    @Query(value = "SELECT ap.id as Id, ap.account_id as accountId, ap.app_time as appTime, ap.link_cv as linkCv,\n" +
            "ap.message as message, p.company as company, p.position as position, p.id as postId, ap.type as type FROM apply_post as ap\n" +
            "INNER JOIN post as p ON ap.post_id = p.id\n" +
            "WHERE ap.account_id = :id ORDER BY ap.id DESC", nativeQuery = true)
    List<MyApplyPostItfDto> getApplyPostByAcocuntId(@Param("id") Long id);

    @Query(value = "SELECT ap FROM ApplyPost ap WHERE ap.accountId = :accountId AND ap.postId = :postId")
    ApplyPost checkApply(@Param("accountId") Long accountId, @Param("postId") Long postId);
}
