package com.entity;

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

    @Column(name = "status", columnDefinition = "NVARCHAR(50) DEFAULT 'Active'") // Trạng thái mặc định
    private String status;


    @Column(name = "deletedAt") // Thời điểm bị xóa
    private LocalDateTime deletedAt;

    // Default constructor
    public ProductGroup() {}

    // Parameterized constructor
    public ProductGroup(String productGroupId, String groupName, String parentGroupId, String status,
			LocalDateTime deletedAt) {
		super();
		this.productGroupId = productGroupId;
		this.groupName = groupName;
		this.parentGroupId = parentGroupId;
		this.status = status;
		this.deletedAt = deletedAt;
	}

    // Getters and Setters
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


    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

	@Override
	public String toString() {
		return "ProductGroup [productGroupId=" + productGroupId + ", groupName=" + groupName + ", parentGroupId="
				+ parentGroupId + ", status=" + status + ", deletedAt=" + deletedAt + "]";
	}

    
}
