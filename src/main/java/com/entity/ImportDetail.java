package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "ImportDetail")
public class ImportDetail implements Serializable {

    @EmbeddedId
    private ImportDetailId id;

    @MapsId("importId") // Liên kết với importId trong composite key
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "importId")
    private Import importEntity;

    @Column(name = "price")
    private double price;

    @Column(name = "amount")
    private int amount;

    public ImportDetail() {
        super();
    }

    public ImportDetail(ImportDetailId id, Import importEntity, double price, int amount) {
        this.id = id;
        this.importEntity = importEntity;
        this.price = price;
        this.amount = amount;
    }

    // Getters và Setters
    public ImportDetailId getId() { 
    	return id;
    }
    
    public void setId(ImportDetailId id) { 
		this.id = id; 
	}
    
    public Import getImportEntity() { 
		return importEntity; 
	}

    public void setImportEntity(Import importEntity) { 
		this.importEntity = importEntity; 
	}

    public double getPrice() { 
		return price; 
	}

    public void setPrice(double price) { 
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
        return "ImportDetail [id=" + id + ", price=" + price + ", amount=" + amount + "]";
    }
}