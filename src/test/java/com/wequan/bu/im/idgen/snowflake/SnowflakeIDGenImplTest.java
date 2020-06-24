package com.wequan.bu.im.idgen.snowflake;

import com.wequan.bu.im.idgen.IDGen;
//import com.wequan.bu.im.idgen.common.PropertyFactory;
import com.wequan.bu.im.idgen.common.Result;
import org.junit.Test;

//import java.util.Properties;

// --add-opens java.base/jdk.internal.misc=ALL-UNNAMED
public class SnowflakeIDGenImplTest {
    @Test
    public void testGetId() {
//        Properties properties = PropertyFactory.getProperties();

//        IDGen idGen = new SnowflakeIDGenImpl(properties.getProperty("leaf.zk.list"), 8080);
        IDGen idGen = SnowflakeIDGenImpl.getInstance();
        for (int i = 1; i < 1000; ++i) {
            Result r = idGen.get("a");
            System.out.println(r);
        }
    }
}
