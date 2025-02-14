package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "minimumAmount", precision = 18, scale = 2)
    private double minimumAmount;

    @Column(name = "status")
    private String status = "Active";

    @ElementCollection
    @CollectionTable(
        name = "UsersDiscount",
        joinColumns = @JoinColumn(name = "discountId")
    )
    @Column(name = "userEmail")
    private Set<String> userEmails = new HashSet<>();

    public Discount()
    {
    	
    }
     
    
    public Discount(String discountId, String discountDesc, Integer discountAmount, double minimumAmount,
			String status) {
		super();
		this.discountId = discountId;
		this.discountDesc = discountDesc;
		this.discountAmount = discountAmount;
		this.minimumAmount = minimumAmount;
		this.status = status;
	}


	// Getters and Setters
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

    public String getStatus() {
        return status;
    }

	public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getUserEmails() {
        return userEmails;
    }

    public void setUserEmails(Set<String> userEmails) {
        this.userEmails = userEmails;
    }
    
    @Override
   	public String toString() {
   		return "Discount [discountId=" + discountId + ", discountDesc=" + discountDesc + ", discountAmount="
   				+ discountAmount + ", minimumAmount=" + minimumAmount + ", status=" + status + ", userEmails="
   				+ userEmails + "]";
   	}
}
