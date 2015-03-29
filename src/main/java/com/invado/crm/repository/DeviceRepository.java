package com.invado.crm.repository;

import com.invado.crm.domain.Device;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Device entity.
 */
public interface DeviceRepository extends JpaRepository<Device,Long>{

}
