package org.example.rest;

import java.math.BigDecimal;

public class ServiceDto {

    private Long id;
    private BigDecimal amount;
	private String description;

	public ServiceDto() {
	}

	public ServiceDto(Long id, BigDecimal amount, String description) {
		this.id = id;
		this.amount = amount;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
