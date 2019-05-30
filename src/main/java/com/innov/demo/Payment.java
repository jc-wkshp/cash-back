package com.innov.demo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Payment {

	  private Integer id;
    private String name;
    private Double amount;
    //private String details;
    
    public Payment() {}

    public Payment(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    // public Payment(String name, Double amount, String details) {
    //     this.name = name;
    //     this.amount = amount;
    //     this.details = details;
    // }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
    }
    
    public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
    }

    
  // public String getDetails() {
	// 	return details;
	// }

	// public void setAmount(String details) {
	// 	this.details = details;
  // }

  @Override
  public String toString() {
    return ToStringBuilder .reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}