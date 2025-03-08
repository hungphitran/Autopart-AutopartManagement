package com.entity;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
@Table(name = "ProductGroup") // Ánh xạ với bảng ProductGroup trong database
public class ProductGroup {

    @Id
    @Column(name="productGroupId")
    private String productGroupId;
    
    @Column(name = "groupName") // Khóa chính: groupName
    private String groupName;

    @Column(name = "parentGroupId") // Khóa ngoại: parentGroup
    private String parentGroupId;

    @Column(name = "status", columnDefinition = "NVARCHAR(50)") // Trạng thái mặc định
    private String status;

    @Column(name = "deletedAt") // Thời điểm bị xóa
    private Timestamp  deletedAt;
    
    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    
    @Column(name="deleted")
    private boolean deleted;

    // Default constructor
    public ProductGroup() {}

	public ProductGroup(String productGroupId, String groupName, String parentGroupId, String status,
			Timestamp deletedAt, Timestamp createdAt, Timestamp updatedAt, boolean deleted) {
		super();
		this.productGroupId = productGroupId;
		this.groupName = groupName;
		this.parentGroupId = parentGroupId;
		this.status = status;
		this.deletedAt = deletedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
	}

	public String getProductGroupId() {
		return productGroupId;
	}

	public void setProductGroupId(String productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(String parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Timestamp deletedAt) {
		this.deletedAt = deletedAt;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public String toString() {
		return "ProductGroup [productGroupId=" + productGroupId + ", groupName=" + groupName + ", parentGroupId="
				+ parentGroupId + ", status=" + status + ", deletedAt=" + deletedAt + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deleted=" + deleted + "]";
	}

    
}
