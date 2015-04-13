package com.invado.crm.repository;

import com.invado.crm.domain.BusinessPartnerContactDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the BusinessPartnerContactDetails entity.
 */
public interface BusinessPartnerContactDetailsRepository extends JpaRepository<BusinessPartnerContactDetails,Long>{

    @Query(value = "SELECT bc from BusinessPartnerContactDetails bc where bc.businessPartner.id = :partnerId")
    public List<BusinessPartnerContactDetails> findAllPerPartnerId(@Param("partnerId") Long partnerId);

}
