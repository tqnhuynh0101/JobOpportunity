package com.jot.JobOpportunity.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "apply_post")
public class ApplyPost extends BaseEntity{

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", columnDefinition = "BIGINT", nullable = false)
    private Long accountId;

    @Column(name = "post_id", columnDefinition = "BIGINT", nullable = false)
    private Long postId;

    @Column(name = "poster_id", columnDefinition = "BIGINT", nullable = false)
    private Long posterId;

    @Column(name = "app_time", length = 50)
    private String appTime;

    @Column(name = "link_cv", columnDefinition = "VARCHAR(500)")
    private String linkCv;

    @Column(name = "type", columnDefinition = "TINYINT DEFAULT 0")
    private Integer type;

    @Column(name = "message", columnDefinition = "VARCHAR(500)")
    private String message;

    @Column(name = "cv_id", columnDefinition = "BIGINT", nullable = false)
    private Long cvId;

    public ApplyPost() {
    }

    public Long getCvId() {
        return cvId;
    }

    public void setCvId(Long cvId) {
        this.cvId = cvId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getAppTime() {
        return appTime;
    }

    public void setAppTime(String appTime) {
        this.appTime = appTime;
    }

    public String getLinkCv() {
        return linkCv;
    }

    public void setLinkCv(String linkCv) {
        this.linkCv = linkCv;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getPosterId() {
        return posterId;
    }

    public void setPosterId(Long posterId) {
        this.posterId = posterId;
    }
}
