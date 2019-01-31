/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.dao.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.nuance.him.config.DatabaseConfig;
import com.nuance.him.dao.atm.SpringAtmDao;
import com.nuance.him.dao.atm.SpringAtmDaoImpl;
import com.nuance.him.dao.daoexception.AtmDaoException;
import com.nuance.him.model.atm.AtmDetail;
import java.util.List;
import static org.testng.Assert.assertNotNull;

/**
 * test {@link SpringAtmDaoImpl}.
 */
@TestPropertySource("classpath:sql-queries.xml")
@ContextConfiguration(classes = DatabaseConfig.class)
@Transactional
public class SpringAtmDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

    private static final String ISSUE_ATM = "issueAtm";
    private static final String IS_ATM_ALREADY_TAKEN = "isAtmAlreadyTaken";
    private static final String DISPLAY_ALL_ATM = "getAllAtmDetail";
    private static final int ATM_NUMBER = 123456;
    private static final int ACC_NUMBER = 3;
    private static final int CVV_NUMBER = 723;
    private static final String CARD_TYPE = "visa";
    @Value("${" + ISSUE_ATM + "}")
    private String getIssueAtm;
    @Value("${" + IS_ATM_ALREADY_TAKEN + "}")
    private String getIsAtmAlreadyTaken;
    @Value("${" + DISPLAY_ALL_ATM + "}")
    private String getDisplayAllAtm;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private SpringAtmDao springAtmDao;
    private AtmDetail atmDetail;

    /**
     * initial setup.
     */
    @BeforeMethod
    public void setUp() {
        springAtmDao = new SpringAtmDaoImpl(namedParameterJdbcTemplate, getIssueAtm,getIsAtmAlreadyTaken,getDisplayAllAtm);
        atmDetail = new AtmDetail(ATM_NUMBER, ACC_NUMBER, CVV_NUMBER, CARD_TYPE);
    }

    /**
     * test issueAtm.
     *
     * @throws Exception exception if fail to issue
     */
    @Test
    @Transactional
    public void testIssueAtm() throws AtmDaoException {
        assertNotNull(springAtmDao, "springAccountDAO should not be null");
        final AtmDetail atmDetailResult = springAtmDao.issueAtmCard(atmDetail);
        assertNotNull(atmDetailResult, "atmDetailResult should not null");
    }

    /**
     * test isAtmAlready Taken working fine from dao.
     * @throws Exception {@link AtmDaoException}
     */
    @Test
    @Transactional
    public void testIsAtmAlreadyTaken() throws AtmDaoException {
        assertNotNull(springAtmDao, "springAccountDAO should not be null");
        final AtmDetail atmDetailResult = springAtmDao.isAlreadyAtmTaken(ACC_NUMBER);
        assertNotNull(atmDetailResult, "atmDetailResult should not null");

    }

    /**
     * test Display All Atm Details.
     * @throws Exception {@link AtmDaoException}
     */
    @Test
    @Transactional
    public void testDisplayAllAtmDetail() throws AtmDaoException {
        assertNotNull(springAtmDao, "springAccountDAO should not be null");
        final List<AtmDetail> atmDetails = springAtmDao.displayAllAtmDetail();
        assertNotNull(atmDetails, "atmDetails should not null");

    }
}
