package com.devexperts.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devexperts.model.account.Account;
@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
}
