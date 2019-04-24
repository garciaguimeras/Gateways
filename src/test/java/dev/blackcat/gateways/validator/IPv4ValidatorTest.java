package dev.blackcat.gateways.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IPv4ValidatorTest {

    @Autowired
    IPv4Validator ipv4Validator;

    @Test
    public void testIpOk() {
        Assert.assertTrue(ipv4Validator.isValid("127.0.0.1"));
    }

    @Test
    public void testIpOutOfRange() {
        Assert.assertFalse(ipv4Validator.isValid("127.0.0.260"));
    }

    @Test
    public void testNotIp() {
        Assert.assertFalse(ipv4Validator.isValid("some string"));
    }

    @Test
    public void testUncompleteIp() {
        Assert.assertFalse(ipv4Validator.isValid("127.0.1"));
    }

}
