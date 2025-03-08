package com.entity;

import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "GeneralSettings")
public class GeneralSettings {
	@Id
    @Column(name = "websiteName", nullable = false, length = 255)
    private String websiteName;  // Website name (Primary Key)

    @Column(name = "logo", length = 255)
    private String logo;         // URL of the logo

    @Column(name = "phone", length = 15)
    private String phone;        // Phone number

    @Column(name = "email", length = 255)
    private String email;        // Email address

    @Column(name = "address", length = 255)
    private String address;      // Company address

    @Column(name = "copyright", length = 255)
    private String copyright;    // Copyright information

    @Column(name = "createdAt")
    private Timestamp createdAt;
    
    @Column(name = "updatedAt")
    private Timestamp updatedAt;
    
    @Column(name ="deletedAt")
    private Timestamp deletedAt;
    
    @Column(name="deleted")
    private boolean deleted;
    
    // Default constructor
    public GeneralSettings() {}

	public GeneralSettings(String websiteName, String logo, String phone, String email, String address,
			String copyright, Timestamp createdAt, Timestamp updatedAt, Timestamp deletedAt, boolean deleted) {
		super();
		this.websiteName = websiteName;
		this.logo = logo;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.copyright = copyright;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
		this.deleted = deleted;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
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
		return "GeneralSettings [websiteName=" + websiteName + ", logo=" + logo + ", phone=" + phone + ", email="
				+ email + ", address=" + address + ", copyright=" + copyright + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + ", deletedAt=" + deletedAt + ", deleted=" + deleted + "]";
	}

	
}
   
