package dev.blackcat.gateways.repository;

import dev.blackcat.gateways.entity.Gateway;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GatewayRepository extends JpaRepository<Gateway, String> {


    @Query("SELECT g FROM Gateway g WHERE :uid IN (SELECT d.uid FROM g.devices d)")
    public Gateway findByDeviceUid(@Param("uid") Long uid);

}
