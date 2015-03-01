package com.invado.crm.repository;

import com.invado.crm.domain.ClassificationType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ClassificationType entity.
 */
public interface ClassificationTypeRepository extends JpaRepository<ClassificationType,Long>{

}
