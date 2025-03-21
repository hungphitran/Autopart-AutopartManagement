package com.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ImportDetailId implements Serializable {
    private String importId;
    private String productId;

    // Constructors, getters, setters, equals, hashCode
    public ImportDetailId() {}

    public ImportDetailId(String importId, String productId) {
        this.importId = importId;
        this.productId = productId;
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
}