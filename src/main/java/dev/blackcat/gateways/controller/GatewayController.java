package dev.blackcat.gateways.controller;

import dev.blackcat.gateways.controller.dto.Response;
import dev.blackcat.gateways.entity.Device;
import dev.blackcat.gateways.entity.Gateway;
import dev.blackcat.gateways.service.GatewayException;
import dev.blackcat.gateways.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/gateways")
public class GatewayController {

    @Autowired
    private GatewayService gatewayService;

    @RequestMapping(value = "/")
    public ResponseEntity<List<Gateway>> getGateways() {
        List<Gateway> list = gatewayService.getGateways();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/get/{serialNumber}")
    public ResponseEntity<Gateway> getGateway(@PathVariable String serialNumber) {
        Gateway gateway = gatewayService.getGateway(serialNumber);
        return ResponseEntity.ok(gateway);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Response> addGateway(@RequestParam String serialNumber, @RequestParam String name, @RequestParam String ipv4) {
        try {
            gatewayService.addGateway(serialNumber, name, ipv4);
        }
        catch (GatewayException e) {
            return ResponseEntity.ok(Response.error(e.getMessage()));
        }
        return ResponseEntity.ok(Response.ok());
    }

    @RequestMapping(value = "/remove/{serialNumber}")
    public ResponseEntity<Response> removeGateway(@PathVariable String serialNumber) {
        try {
            gatewayService.removeGateway(serialNumber);
        }
        catch (GatewayException e) {
            return ResponseEntity.ok(Response.error(e.getMessage()));
        }
        return ResponseEntity.ok(Response.ok());
    }

    @RequestMapping(value = "/add-device", method = RequestMethod.POST)
    public ResponseEntity<Response> addDevice(@RequestParam String serialNumber, @RequestParam Long uid, @RequestParam String vendor, @RequestParam Device.State state) {
        try {
            gatewayService.addDevice(uid, vendor, state, serialNumber);
        }
        catch (GatewayException e) {
            return ResponseEntity.ok(Response.error(e.getMessage()));
        }
        return ResponseEntity.ok(Response.ok());
    }

    @RequestMapping(value = "/remove-device/{uid}")
    public ResponseEntity<Response> removeDevice(@PathVariable Long uid) {
        try {
            gatewayService.removeDevice(uid);
        }
        catch (GatewayException e) {
            return ResponseEntity.ok(Response.error(e.getMessage()));
        }
        return ResponseEntity.ok(Response.ok());
    }

}
