package dev.blackcat.gateways.entity;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Gateway {

    @Id
    private String serialNumber;

    private String name;

    private String ipv4;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Device> devices;

    public Gateway() {
    }

    public Gateway(String serialNumber, String name, String ipv4) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.ipv4 = ipv4;
        this.devices = new ArrayList<>();
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
