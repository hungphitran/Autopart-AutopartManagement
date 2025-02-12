package com.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Employee")
public class Employee {
	@Id
	@Column(name = "email")
    private String email;

    @Column(name = "roleGroupId")
    private String roleGroupId;

    // Default constructor
    public Employee() {
        super();
    }

    // Parameterized constructor
    public Employee(String email, String roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

    // Getter and Setter for roleGroupId
    public String getRoleGroupId() {
        return roleGroupId;
    }

    public void setRoleGroupId(String roleGroupId) {
        this.roleGroupId = roleGroupId;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Employee [email=" + email + ", roleGroupId=" + roleGroupId + "]";
	}
    
   
}
