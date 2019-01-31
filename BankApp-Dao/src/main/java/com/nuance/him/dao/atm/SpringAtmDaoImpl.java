/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.dao.atm;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import com.nuance.him.dao.daoexception.AtmDaoException;
import com.nuance.him.model.atm.AtmDetail;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

/**
 * implementation of  {@link SpringAtmDao}.
 */
public class SpringAtmDaoImpl implements SpringAtmDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String issueAtmQuery;
    private final String isAlreadyAtmTakenQuery;
    private final String getAllAtmDetail;

    /**
     * constructor of class {@link SpringAtmDaoImpl}.
     *
     * @param namedParameterJdbcTemplate namedParameterJdbcTemplate object.
     * @param issueAtmQuery query for IssueAtm
     * @param isAlreadyAtmTakenQuery check isAtmAlready taken  by accNumber.
     * @param getAllAtmDetail get all AtmData
     */
    public SpringAtmDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate, final String issueAtmQuery, final String isAlreadyAtmTakenQuery, final String getAllAtmDetail) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.issueAtmQuery = issueAtmQuery;
        this.isAlreadyAtmTakenQuery = isAlreadyAtmTakenQuery;
        this.getAllAtmDetail = getAllAtmDetail;
    }

    @Override
    public AtmDetail issueAtmCard(final AtmDetail atmDetail) throws AtmDaoException {
        try {
            final MapSqlParameterSource paramSourceAcc = mapParameterSource(atmDetail);
            namedParameterJdbcTemplate.update(issueAtmQuery, paramSourceAcc);
            return atmDetail;
        }
        catch (final DataAccessException dataAccessException) {
            throw new AtmDaoException("Failed to Issue Atm ", dataAccessException);
        }
    }

    @Override
    public AtmDetail isAlreadyAtmTaken(final int accNumber) throws AtmDaoException {
        final SqlParameterSource namedParameters = new MapSqlParameterSource("accNumber", accNumber);
        try {
            return namedParameterJdbcTemplate.queryForObject(isAlreadyAtmTakenQuery, namedParameters, new AtmMapper());
        }
        catch (final DataAccessException dataAccessException) {
            throw new AtmDaoException("Failed to check Atm taken ", dataAccessException);
        }
    }

    @Override
    public List<AtmDetail> displayAllAtmDetail() throws AtmDaoException {
        try {
            return namedParameterJdbcTemplate.query(getAllAtmDetail, new AtmMapper());
        }
        catch (final DataAccessException dataAccessException) {
            throw new AtmDaoException("Failed to display Atm details", dataAccessException);
        }
    }

    /**
     * method to map sqlParameter.
     *
     * @param atmDetail instance of class {@link AtmDetail}
     * @return instance of {@link MapSqlParameterSource}
     */
    private MapSqlParameterSource mapParameterSource(final AtmDetail atmDetail) {
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        final LocalDate activateDate = LocalDate.now();
        final LocalDate validUpTo = LocalDate.now().plusYears(5L);
        final Random random = new Random();
        final String atmPin = String.format("%04d", random.nextInt(10000));
        atmDetail.setActivateDate(Date.valueOf(activateDate));
        atmDetail.setValidFrom(Date.valueOf(activateDate));
        atmDetail.setValidUpTo(Date.valueOf(validUpTo));
        atmDetail.setAtmPin(Integer.parseInt(atmPin));

        paramSource.addValue("atmNumber", atmDetail.getAtmNumber());
        paramSource.addValue("accNumber", atmDetail.getAccNumber());
        paramSource.addValue("cvvNumber", atmDetail.getCvvNumber());
        paramSource.addValue("atmPin", atmDetail.getAtmPin());
        paramSource.addValue("activateDate", atmDetail.getActivateDate());
        paramSource.addValue("validFrom", atmDetail.getValidFrom());
        paramSource.addValue("validUpTo", atmDetail.getValidUpTo());
        paramSource.addValue("cardType", atmDetail.getAtmType());
        return paramSource;
    }
}
