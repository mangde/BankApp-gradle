/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.test.atm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.nuance.him.config.atm.AtmControllerConfig;
import com.nuance.him.controller.atm.AtmController;
import static org.testng.Assert.assertNotNull;

/**
 * {@link AtmControllerConfigTest} Test class.
 */
@ContextConfiguration(classes = { AtmControllerConfig.class })
public class AtmControllerConfigTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private AtmController atmController;

    /**
     * {@link AtmController} atmService bean testing.
     */
    @Test
    public void testAtmController() {
        assertNotNull(atmController);
    }
}
