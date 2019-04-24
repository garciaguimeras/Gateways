package dev.blackcat.gateways.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Device {

    public enum State {
        OFFLINE,
        ONLINE
    }

    @Id
    private Long uid;

    private String vendor;

    @CreationTimestamp
    private Date creationDate;

    @Enumerated(EnumType.STRING)
    private State state;

    public Device() {
    }

    public Device(Long uid, String vendor, State state) {
        this.uid = uid;
        this.vendor = vendor;
        this.creationDate = creationDate;
        this.state = state;
    }

    public Long getUid() {
        return uid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
