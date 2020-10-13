package tech.ibit.structlog4j.extend;

import lombok.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author IBIT程序猿
 */
public class MapToLogTest {


    @Test
    public void toLog() {
        User user = new User("ibit-tech", 21);
        Assert.assertEquals("[age, 21, name, ibit-tech]", Arrays.asList(user.toLog()).toString());

        User vipUser = new VipUser("ibit-tech-vip", 21, true);
        Assert.assertEquals("[age, 21, name, ibit-tech-vip, vip, true]", Arrays.asList(vipUser.toLog()).toString());
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class User implements MapToLog {
        private String name;
        private int age;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class VipUser extends User {
        private boolean vip;

        public VipUser(String name, int age, boolean vip) {
            super(name, age);
            this.vip = vip;
        }
    }
}