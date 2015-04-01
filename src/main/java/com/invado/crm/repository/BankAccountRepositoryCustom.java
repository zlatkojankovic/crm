package com.invado.crm.repository;

import com.invado.crm.domain.BankAccount;

import java.util.List;

/**
 * Created by Zlatko on 3/31/2015.
 */
public interface BankAccountRepositoryCustom {

    public List<BankAccount> findAllPerPartnerId(Long partnerId);
}
