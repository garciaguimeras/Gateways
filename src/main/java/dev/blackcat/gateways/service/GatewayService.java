package dev.blackcat.gateways.service;

import dev.blackcat.gateways.controller.dto.Response;
import dev.blackcat.gateways.entity.Device;
import dev.blackcat.gateways.entity.Gateway;
import dev.blackcat.gateways.repository.DeviceRepository;
import dev.blackcat.gateways.repository.GatewayRepository;
import dev.blackcat.gateways.validator.IPv4Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GatewayService {

    private final static int MAX_GATEWAY_DEVICES = 10;

    @Autowired
    private GatewayRepository gatewayRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private IPv4Validator ipv4Validator;

    @Transactional
    public List<Gateway> getGateways() {
        List<Gateway> list = gatewayRepository.findAll();
        for (Gateway g: list) {
            g.getDevices().size();
        }
        return list;
    }

    @Transactional
    public Gateway getGateway(String serialNumber) {
        Gateway g = gatewayRepository.findOne(serialNumber);
        if (g != null) {
            g.getDevices().size();
        }
        return g;
    }

    @Transactional
    public void addGateway(String serialNumber, String name, String ipv4) throws GatewayException {
        Gateway gateway = gatewayRepository.findOne(serialNumber);
        if (gateway != null) {
            throw new GatewayException("Gateway with serial number " + serialNumber + " already exists");
        }

        if (!ipv4Validator.isValid(ipv4)) {
            throw new GatewayException("IPv4 address " + ipv4 + " is not valid");
        }

        gateway = new Gateway(serialNumber, name, ipv4);
        gatewayRepository.save(gateway);
    }

    @Transactional
    public void removeGateway(String serialNumber) throws GatewayException {
        Gateway gateway = gatewayRepository.findOne(serialNumber);
        if (gateway == null) {
            throw new GatewayException("Gateway with serial number " + serialNumber + " does not exist");
        }

        gatewayRepository.delete(serialNumber);
    }

    @Transactional
    public void addDevice(Long uid, String vendor, Device.State state, String serialNumber) throws GatewayException {
        Device device = deviceRepository.findOne(uid);
        if (device != null) {
            throw new GatewayException("Device with UID " + uid + " already exists");
        }

        Gateway gateway = gatewayRepository.findOne(serialNumber);
        if (gateway == null) {
            throw new GatewayException("Gateway with serial number " + serialNumber + " does not exist");
        }
        if (gateway.getDevices().size() >= MAX_GATEWAY_DEVICES) {
            throw new GatewayException("Gateway with serial number " + serialNumber + " cannot allow more devices");
        }

        device = new Device(uid, vendor, state);
        gateway.getDevices().add(device);
        gatewayRepository.save(gateway);
    }

    @Transactional
    public void removeDevice(Long uid) throws GatewayException {
        Device device = deviceRepository.findOne(uid);
        if (device == null) {
            throw new GatewayException("Device with UID " + uid + " does not exist");
        }

        Gateway gateway = gatewayRepository.findByDeviceUid(device.getUid());
        if (gateway == null)
            throw new GatewayException("Device with UID " + uid + " does not exist");

        gateway.getDevices().remove(device);
        gatewayRepository.save(gateway);
    }

}
