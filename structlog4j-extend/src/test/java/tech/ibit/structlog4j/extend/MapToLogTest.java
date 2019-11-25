package tech.ibit.structlog4j.extend;

import lombok.Value;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author IBIT-TECH
 */
public class MapToLogTest {


    @Test
    public void toLog() {
        User user = new User("ibit-tech", 21);
        Assert.assertEquals("[name, ibit-tech, age, 21]", Arrays.asList(user.toLog()).toString());
    }

    @Value
    public class User implements MapToLog {
        private String name;
        private int age;
    }
}