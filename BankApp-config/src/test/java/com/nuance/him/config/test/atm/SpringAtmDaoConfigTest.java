/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.nuance.him.config.atm.AtmDaoConfig;
import com.nuance.him.dao.atm.SpringAtmDao;

/**
 * {@link SpringAtmDaoConfigTest}.
 */
@ContextConfiguration(classes = AtmDaoConfig.class)
public class SpringAtmDaoConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SpringAtmDao springAtmDao;

    /**
     * for bean class of AtmDao.
     */
    @Test
    public void testAtmDao() {
        Assert.assertNotNull(springAtmDao);
    }
}
