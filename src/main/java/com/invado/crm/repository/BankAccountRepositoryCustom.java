package com.invado.crm.repository;

import com.invado.crm.domain.BankAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Zlatko on 3/31/2015.
 */
public interface BankAccountRepositoryCustom {

    @Query("SELECT ba from BankAccount ba where ba.businessPartner.id = :partnerId")
    public List<BankAccount> findAllPerPartnerId(@Param("partnerId") Long partnerId);
}
