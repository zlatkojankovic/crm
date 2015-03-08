package com.invado.crm.repository;

import com.invado.crm.domain.BusinessPartner;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BusinessPartner entity.
 */
public interface BusinessPartnerRepository extends JpaRepository<BusinessPartner,Long>{

}
