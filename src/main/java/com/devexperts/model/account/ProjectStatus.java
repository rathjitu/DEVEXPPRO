package com.devexperts.model.account;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonDeserialize(contentAs= ProjectStatus.class)
public class ProjectStatus {
	private String status;

	public ProjectStatus() {

	}

	public ProjectStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}