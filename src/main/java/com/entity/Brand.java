package com.entity;

import java.sql.Timestamp;
import javax.persistence.*;

@Entity
@Table(name = "Brand") // Ánh xạ class với bảng Brand trong cơ sở dữ liệu
public class Brand {

    @Id
    @Column(name ="brandId")
    private String brandId;
    
    @Column(name = "brandName") // Khóa chính: Tên thương hiệu
    private String brandName;

    @Column(name = "status", columnDefinition = "NVARCHAR(50) DEFAULT 'Active'") // Trạng thái mặc định
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
    public Brand() {}

	public Brand(String brandId, String brandName, String status, Timestamp deletedAt, Timestamp createdAt,
			Timestamp updatedAt, boolean deleted) {
		super();
		this.brandId = brandId;
		this.brandName = brandName;
		this.status = status;
		this.deletedAt = deletedAt;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deleted = deleted;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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
		return "Brand [brandId=" + brandId + ", brandName=" + brandName + ", status=" + status + ", deletedAt="
				+ deletedAt + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", deleted=" + deleted + "]";
	}

	
   
}
