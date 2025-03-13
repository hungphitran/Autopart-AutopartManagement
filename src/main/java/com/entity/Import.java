package com.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "Import")
public class Import {

    @Id
    @Column(name = "importId")
    private String importId;

    @Column(name = "employeePhone", nullable = false)
    private String employeePhone;

    @Column(name = "importDate", nullable = false)
    private java.sql.Date importDate;

    @Column(name = "importCost")
    private BigDecimal importCost;


    @OneToMany(mappedBy = "importId", fetch = FetchType.EAGER)
    private List<ImportDetail> importDetails;


    public Import() {
        super();
    }

    public Import(String importId, String employeePhone, Date importDate, BigDecimal importCost,  List<ImportDetail> importDetails) {
        super();
        this.importId = importId;
        this.employeePhone = employeePhone;
        this.importDate = importDate;
        this.importCost = importCost;
        this.importDetails = importDetails;
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

    @Override
    public String toString() {
        return "Import [importId=" + importId + ", employeePhone=" + employeePhone + ", importDate=" + importDate
                + ", importCost=" + importCost + ", importDetails=" + importDetails + "]";
    }
}