package com.jot.JobOpportunity.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Base entity: createBy, createTime, updateBy, updateTime
 **/
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    @Column(name = "create_by", nullable = false, length = 50)
    @JsonIgnore
    private String createBy;

    @Column(name = "create_time", length = 50, nullable = false)
    @JsonIgnore
    private String createTime;

    @Column(name = "update_by", length = 50)
    @JsonIgnore
    private String updateBy;

    @Column(name = "update_time", length = 50)
    @JsonIgnore
    private String updateTime;

    @Column(name = "remark", length = 500)
    @JsonIgnore
    private String remark;
    
	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
