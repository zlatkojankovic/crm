package com.invado.crm.repository;

import com.invado.crm.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BankAccount entity.
 */
public interface BankAccountRepository extends JpaRepository<BankAccount,Long>{

}
