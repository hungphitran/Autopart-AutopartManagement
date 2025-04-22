package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Table(name = "Import")
public class Import {
	@Transient // Not stored in the database, just for convenience
    private String employeeName;
	
    @Id
    @Column(name = "importId")
    private String importId;

    @Column(name = "employeeEmail", nullable = false)
    private String employeeEmail;
    
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "employeeEmail", insertable = false, updatable = false, referencedColumnName = "email")
    private Employee employee; 

    @Column(name = "importDate", nullable = false)
    private java.sql.Date importDate;

    @Column(name = "importCost")
    private BigDecimal importCost;


    @OneToMany(mappedBy = "importEntity", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImportDetail> importDetails;

    
    public Import() {
        super();
    }


	public Import(String employeeName, String importId, String employeeEmail, Employee employee, Date importDate,
			BigDecimal importCost, List<ImportDetail> importDetails) {
		super();
		this.employeeName = employeeName;
		this.importId = importId;
		this.employeeEmail = employeeEmail;
		this.employee = employee;
		this.importDate = importDate;
		this.importCost = importCost;
		this.importDetails = importDetails;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getImportId() {
		return importId;
	}


	public void setImportId(String importId) {
		this.importId = importId;
	}


	public String getEmployeeEmail() {
		return employeeEmail;
	}


	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}


	public Employee getEmployee() {
		return employee;
	}


	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public java.sql.Date getImportDate() {
		return importDate;
	}


	public void setImportDate(java.sql.Date importDate) {
		this.importDate = importDate;
	}


	public BigDecimal getImportCost() {
		return importCost;
	}


	public void setImportCost(BigDecimal importCost) {
		this.importCost = importCost;
	}


	public List<ImportDetail> getImportDetails() {
		return importDetails;
	}


	public void setImportDetails(List<ImportDetail> importDetails) {
		this.importDetails = importDetails;
	}


	@Override
	public String toString() {
		return "Import [employeeName=" + employeeName + ", importId=" + importId + ", employeeEmail=" + employeeEmail
				+ ", employee=" + employee + ", importDate=" + importDate + ", importCost=" + importCost
				+ ", importDetails=" + importDetails + "]";
	}

}