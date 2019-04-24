package dev.blackcat.gateways.repository;

import dev.blackcat.gateways.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
