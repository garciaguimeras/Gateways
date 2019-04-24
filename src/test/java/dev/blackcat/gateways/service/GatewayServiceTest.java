package dev.blackcat.gateways.service;

import dev.blackcat.gateways.entity.Device;
import dev.blackcat.gateways.entity.Gateway;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayServiceTest {

    private static final String SERIAL_NUMBER_1 = "SERIALNMBR1";

    @Autowired
    GatewayService gatewayService;

    private void createGateway1() {
        try {
            gatewayService.addGateway(SERIAL_NUMBER_1, "Gateway 1", "218.34.35.76");
        }
        catch (GatewayException e) {
            Assert.fail(e.getMessage());
        }
    }

    private void removeGateway1() {
        try {
            gatewayService.removeGateway(SERIAL_NUMBER_1);
        }
        catch (GatewayException e) {
            Assert.fail(e.getMessage());
        }
    }

    private void createDevice1() {
        try {
            gatewayService.addDevice(1L, "Vendor1", Device.State.OFFLINE, SERIAL_NUMBER_1);
        }
        catch (GatewayException e) {
            Assert.fail(e.getMessage());
        }
    }

    private void removeDevice1() {
        try {
            gatewayService.removeDevice(1L);
        }
        catch (GatewayException e) {
            Assert.fail(e.getMessage());
        }
    }

    private void createDevice(long uid) {
        try {
            gatewayService.addDevice(uid, "Vendor1", Device.State.OFFLINE, SERIAL_NUMBER_1);
        }
        catch (GatewayException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void testInsertGateway() {
        createGateway1();

        Gateway g = gatewayService.getGateway(SERIAL_NUMBER_1);
        Assert.assertNotNull(g);
        Assert.assertEquals(SERIAL_NUMBER_1, g.getSerialNumber());

        removeGateway1();
    }

    @Test
    public void testInsertTwoGatewaysWithSameSerialNumber() {
        createGateway1();

        try {
            gatewayService.addGateway(SERIAL_NUMBER_1, "Gateway 2", "218.34.35.77");
            Assert.fail("Could not accept two gateways with the same serial number");
        }
        catch (GatewayException e) {
        }

        removeGateway1();
    }

    @Test
    public void testInsertGatewaysWithMalformedIpv4() {
        try {
            gatewayService.addGateway(SERIAL_NUMBER_1, "Gateway 1", "218.34.3500.77");
            Assert.fail("Could not accept a malformed IPv4");
        }
        catch (GatewayException e) {
        }

        Gateway g = gatewayService.getGateway(SERIAL_NUMBER_1);
        Assert.assertNull(g);
    }

    @Test
    public void testInsertGatewayWithDevice() {
        createGateway1();
        createDevice1();

        Gateway g = gatewayService.getGateway(SERIAL_NUMBER_1);
        Assert.assertNotNull(g);
        Assert.assertEquals(1, g.getDevices().size());
        Assert.assertEquals(1, g.getDevices().get(0).getUid().longValue());

        removeGateway1();
    }

    @Test
    public void testInsertGatewayWithDeviceAndRemoveIt() {
        createGateway1();
        createDevice1();

        Gateway g = gatewayService.getGateway(SERIAL_NUMBER_1);
        Assert.assertNotNull(g);
        Assert.assertEquals(1, g.getDevices().size());
        Assert.assertEquals(1, g.getDevices().get(0).getUid().longValue());

        removeDevice1();

        g = gatewayService.getGateway(SERIAL_NUMBER_1);
        Assert.assertNotNull(g);
        Assert.assertEquals(0, g.getDevices().size());

        removeGateway1();
    }

    @Test
    public void testInsertGatewayWithMoreThan10Devices() {
        createGateway1();

        for (long i = 0; i < 10; i++) {
            createDevice(i);
        }
        Gateway g = gatewayService.getGateway(SERIAL_NUMBER_1);
        Assert.assertNotNull(g);
        Assert.assertEquals(10, g.getDevices().size());

        try {
            gatewayService.addDevice(11L, "Vendor1", Device.State.OFFLINE, SERIAL_NUMBER_1);
            Assert.fail("Could not accept more than 10 devices");
        }
        catch (GatewayException e) {
        }
        Assert.assertEquals(10, g.getDevices().size());

        removeGateway1();
    }
}
