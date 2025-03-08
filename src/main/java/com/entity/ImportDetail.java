package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "ImportDetail")
public class ImportDetail implements Serializable {

    @Id
    @Column(name = "importId")
    private String importId;

    @Id
    @Column(name = "productId")
    private String productId;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "amount")
    private int amount;

    public ImportDetail() {
        super();
    }

    public ImportDetail(String importId, String productId, BigDecimal price, int amount) {
        super();
        this.importId = importId;
        this.productId = productId;
        this.price = price;
        this.amount = amount;
    }

    public String getImportId() {
        return importId;
    }

    public void setImportId(String importId) {
        this.importId = importId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }


    @Override
    public String toString() {
        return "ImportDetail [importId=" + importId + ", productId=" + productId + ", price=" + price + ", amount="
                + amount + "]";
    }
}