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

    @Column(name = "employeePhone", nullable = false)
    private String employeePhone;
    
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "employeePhone", insertable = false, updatable = false, referencedColumnName = "phone")
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

    public Import(String importId, String employeePhone, Date importDate, BigDecimal importCost,  List<ImportDetail> importDetails, String employeeName) {
		super();
        this.importId = importId;
        this.employeePhone = employeePhone;
        this.importDate = importDate;
        this.importCost = importCost;
        this.importDetails = importDetails;
        this.employeeName = employeeName;
    }

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
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
    
    public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Override
    public String toString() {
        return "Import [importId=" + importId + ", employeePhone=" + employeePhone + ", importDate=" + importDate
                + ", importCost=" + importCost + ", importDetails=" + importDetails + "]";
    }
}