package com.devexperts.model.rest;

import org.springframework.http.ResponseEntity;

public interface AbstractAccountController {
	 public ResponseEntity<Void> transfer(long sourceId, long targetId, double amount) ;
}
