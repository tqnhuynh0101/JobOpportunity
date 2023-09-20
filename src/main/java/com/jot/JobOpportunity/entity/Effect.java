package com.jot.JobOpportunity.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "effect")
public class Effect implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_id", columnDefinition = "BIGINT", nullable = false)
    private Long accountId;

    @Column(name = "post_id", columnDefinition = "BIGINT", nullable = false)
    private Long postId;



    public Effect() {
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
}
