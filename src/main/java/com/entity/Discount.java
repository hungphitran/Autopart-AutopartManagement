package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Discount")
public class Discount {

    @Id
    @Column(name = "discountId")
    private String discountId;

    @Column(name = "discountDesc")
    private String discountDesc;

    @Column(name = "discountAmount")
    private Integer discountAmount;

    @Column(name = "minimumAmount")
    private double minimumAmount;

    @Column(name = "usageLimit")
    private Integer usageLimit;

    @Column(name = "applyStartDate")
    private Date applyStartDate;

    @Column(name = "applyEndDate")
    private Date applyEndDate;

    @Column(name = "status")
    private String status;
    
    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    
    @Column(name ="deletedAt")
    private Timestamp deletedAt;
    
    @Column(name="deleted")
    private boolean deleted;

	public Discount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discount(String discountId, String discountDesc, Integer discountAmount, double minimumAmount,
			Integer usageLimit, Date applyStartDate, Date applyEndDate, String status, Timestamp createdAt,
			Timestamp updatedAt, Timestamp deletedAt, boolean deleted) {
		super();
		this.discountId = discountId;
		this.discountDesc = discountDesc;
		this.discountAmount = discountAmount;
		this.minimumAmount = minimumAmount;
		this.usageLimit = usageLimit;
		this.applyStartDate = applyStartDate;
		this.applyEndDate = applyEndDate;
		this.status = status;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.deleted = deleted;
	}

	public String getDiscountId() {
		return discountId;
	}

	public void setDiscountId(String discountId) {
		this.discountId = discountId;
	}

	public String getDiscountDesc() {
		return discountDesc;
	}

	public void setDiscountDesc(String discountDesc) {
		this.discountDesc = discountDesc;
	}

	public Integer getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Integer discountAmount) {
		this.discountAmount = discountAmount;
	}

	public double getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(double minimumAmount) {
		this.minimumAmount = minimumAmount;
	}

	public Integer getUsageLimit() {
		return usageLimit;
	}

	public void setUsageLimit(Integer usageLimit) {
		this.usageLimit = usageLimit;
	}

	public Date getApplyStartDate() {
		return applyStartDate;
	}

	public void setApplyStartDate(Date applyStartDate) {
		this.applyStartDate = applyStartDate;
	}

	public Date getApplyEndDate() {
		return applyEndDate;
	}

	public void setApplyEndDate(Date applyEndDate) {
		this.applyEndDate = applyEndDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "Discount [discountId=" + discountId + ", discountDesc=" + discountDesc + ", discountAmount="
				+ discountAmount + ", minimumAmount=" + minimumAmount + ", usageLimit=" + usageLimit
				+ ", applyStartDate=" + applyStartDate + ", applyEndDate=" + applyEndDate + ", status=" + status
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", deleted="
				+ deleted + "]";
	}

	
    
}
