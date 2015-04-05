package com.invado.crm.repository;

import com.invado.crm.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BankAccount entity.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount,Long>, BankAccountRepositoryCustom {


}
