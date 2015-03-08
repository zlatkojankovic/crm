package com.invado.crm.repository;

import com.invado.crm.domain.BusinessPartnerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the BusinessPartnerAddress entity.
 */
public interface BusinessPartnerAddressRepository extends JpaRepository<BusinessPartnerAddress,Long>{

}
