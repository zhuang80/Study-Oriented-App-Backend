package com.wequan.bu.im.idgen.segment;

import com.alibaba.druid.pool.DruidDataSource;
import com.wequan.bu.im.idgen.IDGen;
import com.wequan.bu.im.idgen.common.PropertyFactory;
import com.wequan.bu.im.idgen.common.Result;
import com.wequan.bu.im.idgen.segment.dao.IDAllocDao;
import com.wequan.bu.im.idgen.segment.dao.impl.IDAllocDaoImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class IDGenServiceTest {
    IDGen idGen;
    DruidDataSource dataSource;
    @Before
    public void before() throws IOException, SQLException {
        // Load Db Config
        Properties properties = PropertyFactory.getProperties();

        // Config dataSource
        dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getProperty("jdbc.url"));
        dataSource.setUsername(properties.getProperty("jdbc.username"));
        dataSource.setPassword(properties.getProperty("jdbc.password"));
        dataSource.init();

        // Config Dao
        IDAllocDao dao = new IDAllocDaoImpl(dataSource);

        // Config ID Gen
        idGen = new SegmentIDGenImpl();
        ((SegmentIDGenImpl) idGen).setDao(dao);
        idGen.init();
    }
    @Test
    public void testGetId() {
        for (int i = 0; i < 100; ++i) {
            Result r = idGen.get("leaf-segment-test");
            System.out.println(r);
        }
    }
    @After
    public void after() {
       dataSource.close();
    }

}
