package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
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
    private BigDecimal minimumAmount;

    @Column(name = "usageLimit")
    private Integer usageLimit;

    @Column(name = "applyStartDate")
    private Date applyStartDate;

    @Column(name = "applyEndDate")
    private Date applyEndDate;

    @Column(name = "status")
    private String status;

	public Discount() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Discount(String discountId, String discountDesc, Integer discountAmount, BigDecimal minimumAmount,
			Integer usageLimit, Date applyStartDate, Date applyEndDate, String status) {
		super();
		this.discountId = discountId;
		this.discountDesc = discountDesc;
		this.discountAmount = discountAmount;
		this.minimumAmount = minimumAmount;
		this.usageLimit = usageLimit;
		this.applyStartDate = applyStartDate;
		this.applyEndDate = applyEndDate;
		this.status = status;
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

	public BigDecimal getMinimumAmount() {
		return minimumAmount;
	}

	public void setMinimumAmount(BigDecimal minimumAmount) {
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

	@Override
	public String toString() {
		return "Discount [discountId=" + discountId + ", discountDesc=" + discountDesc + ", discountAmount="
				+ discountAmount + ", minimumAmount=" + minimumAmount + ", usageLimit=" + usageLimit
				+ ", applyStartDate=" + applyStartDate + ", applyEndDate=" + applyEndDate + ", status=" + status + "]";
	}
    
    
}
