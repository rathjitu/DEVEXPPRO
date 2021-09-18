package com.devexperts.rest;

import com.devexperts.model.rest.AbstractAccountController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController implements AbstractAccountController {
    public ResponseEntity<Void> transfer(long sourceId, long targetId, double amount) {
        return null;
    }
}
