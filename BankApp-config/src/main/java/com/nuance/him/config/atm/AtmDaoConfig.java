/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.config.atm;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import com.nuance.him.config.DatabaseConfig;
import com.nuance.him.dao.atm.SpringAtmDao;
import com.nuance.him.dao.atm.SpringAtmDaoImpl;
import static org.springframework.util.Assert.notNull;

/**
 * SpringAccountDao {@link Configuration}.
 */
@Configuration
@Import(DatabaseConfig.class)
@PropertySource("classpath:sql-queries.xml")
public class AtmDaoConfig {

    private static final String ISSUE_ATM = "issueAtm";
    private static  final String IS_ATM_ALREADY_TAKEN="isAtmAlreadyTaken";
    private static final String DISPLAY_ALL_ATM="getAllAtmDetail";
    @Value("${" + ISSUE_ATM + "}")
    private String getIssueAtm;
    @Value("${"+IS_ATM_ALREADY_TAKEN+"}")
    private String getIsAtmAlreadyTaken;
    @Value("${"+DISPLAY_ALL_ATM+"}")
    private String getDisplayAllAtm;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Validates namedParameterJdbcTemplate and database queries.
     */
    @PostConstruct
    public void postConstruct() {
        notNull(getIssueAtm, "missing getIssueAtm query");
    }

    /**
     * bean for {@link SpringAtmDao}.
     *
     * @return AtmDaoImpl
     */
    @Bean
    public SpringAtmDao atmDao() {
        return new SpringAtmDaoImpl(namedParameterJdbcTemplate, getIssueAtm,getIsAtmAlreadyTaken,getDisplayAllAtm);
    }
}
