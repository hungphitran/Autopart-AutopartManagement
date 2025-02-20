package com.entity;

import javax.persistence.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Employee")
public class Employee {
	@Id
	@Column(name = "citizenId")
	private String citizenId;
	
	@Column(name = "phone")
    private String phone;


    @Column(name = "fullName")
    private String fullName;
    
    @Column(name = "email")
    private String email;

    @Column(name = "birthDate", columnDefinition = "DATE")
    private Date birthDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "startDate", columnDefinition = "DATE")
    private Date startDate;

    @Column(name = "address")
    private String address;

    @Column(name = "avatar", columnDefinition = "NVARCHAR(MAX)")
    private String avatar;

    @Column(name = "educationLevel")
    private String educationLevel;

    @Column(name = "status")
    private String status;

    @Column(name = "deletedAt", columnDefinition = "DATETIME")
    private Timestamp  deletedAt;
    
    

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Employee(String citizenId, String phone, String fullName, String email,
			Date birthDate, String gender, Date startDate, String address, String avatar,
			String educationLevel, String status, Timestamp  deletedAt) {
		super();
		this.citizenId = citizenId;
		this.phone = phone;
		this.fullName = fullName;
		this.email = email;
		this.birthDate = birthDate;
		this.gender = gender;
		this.startDate = startDate;
		this.address = address;
		this.avatar = avatar;
		this.educationLevel = educationLevel;
		this.status = status;
		this.deletedAt = deletedAt;
	}



	public String getCitizenId() {
		return citizenId;
	}



	public void setCitizenId(String citizenId) {
		this.citizenId = citizenId;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public Date getBirthDate() {
		return birthDate;
	}



	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}



	public String getGender() {
		return gender;
	}



	public void setGender(String gender) {
		this.gender = gender;
	}



	public Date getStartDate() {
		return startDate;
	}



	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getAvatar() {
		return avatar;
	}



	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}



	public String getEducationLevel() {
		return educationLevel;
	}



	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
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



	@Override
	public String toString() {
		return "Employee [citizenId=" + citizenId + ", phone=" + phone + ", fullName="
				+ fullName + ", email=" + email + ", birthDate=" + birthDate + ", gender=" + gender + ", startDate="
				+ startDate + ", address=" + address + ", avatar=" + avatar + ", educationLevel=" + educationLevel
				+ ", status=" + status + ", deletedAt=" + deletedAt + "]";
	}

	
    
    
    
}
