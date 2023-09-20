package com.jot.JobOpportunity.entity;

import javax.persistence.*;

@Entity
@Table(name = "approved_post")
public class ApprovedPost extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "poster_id", columnDefinition = "BIGINT", unique = true)
    private Long posterId;

    @Column(name = "is_free", columnDefinition = "BIT DEFAULT 1")
    private boolean isFree;

    @Column(name = "quantity", columnDefinition = "BIGINT DEFAULT 0")
    private Integer quantity;

    @Column(name = "expired_date", columnDefinition = "VARCHAR(50)")
    private String expiredDate;
    
	public ApprovedPost(Long posterId, boolean isFree, Integer quantity, String expiredDate) {
		super();
		this.posterId = posterId;
		this.isFree = isFree;
		this.quantity = quantity;
		this.expiredDate = expiredDate;
	}

	public ApprovedPost() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPosterId() {
		return posterId;
	}

	public void setPosterId(Long posterId) {
		this.posterId = posterId;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getExpiredDate() {
		return expiredDate;
	}

	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
}
