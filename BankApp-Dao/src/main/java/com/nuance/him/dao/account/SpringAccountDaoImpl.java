/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.account;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.nuance.him.dao.daoexception.AccountDaoException;
import com.nuance.him.model.account.Account;

/**
 * Implementation of interface SpringAccountDao.
 */
public class SpringAccountDaoImpl implements SpringAccountDao {

    private final String getAddAccount;
    private final String getGetAccTypeId;
    private final String getBalance;
    private final String getDeposite;
    private final String accountDetail;
    private final String getWithDraw;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * constructor of class  SpringAccountDaoImpl.
     *
     * @param namedParameterJdbcTemplate NamedParameterJdbcTemplate
     * @param getAddAccount query for addAccount
     * @param getGetAccTypeId query for getAccountTypeId form account type
     * @param getBalance query for check current available balance
     * @param getDeposite query for deposite amount
     * @param accountDetail query for getAccountDetails
     * @param getWithDraw withdraw amount query
     */
    public SpringAccountDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate, final String getAddAccount, final String getGetAccTypeId, final String getBalance, final String getDeposite, final String accountDetail,
        final String getWithDraw) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.getAddAccount = getAddAccount;
        this.getGetAccTypeId = getGetAccTypeId;
        this.getBalance = getBalance;
        this.getDeposite = getDeposite;
        this.accountDetail = accountDetail;
        this.getWithDraw = getWithDraw;
    }

    @Override
    public int addAccount(final Account account, final int accTypeId) throws AccountDaoException {
        final KeyHolder holder = new GeneratedKeyHolder();
        try {
            final MapSqlParameterSource paramSourceAcc = mapParameterSource(account, accTypeId);
            namedParameterJdbcTemplate.update(getAddAccount, paramSourceAcc, holder);
            return holder.getKey().intValue();
        }
        catch (final DataAccessException dataAccessException) {
            throw new AccountDaoException("Failed to add Account ", dataAccessException);
        }
    }

    @Override
    public synchronized double depositeAmount(final int accountNumber, final double amount) throws AccountDaoException {
        final MapSqlParameterSource paramDeposite = toUpdateBalanceParamSource(accountNumber, amount);
        try {
            namedParameterJdbcTemplate.update(getDeposite, paramDeposite);
            return amount;
        }
        catch (final DataAccessException dataAccessException) {
            throw new AccountDaoException("Failed to deposite ", dataAccessException);
        }
    }

    @Override
    public double getCurrentBalance(final int accountNumber) throws AccountDaoException {
        final SqlParameterSource namedParameters = new MapSqlParameterSource("accNumber", accountNumber);
        try {
            return namedParameterJdbcTemplate.queryForObject(getBalance, namedParameters, Double.class);
        }
        catch (final DataAccessException dataAccessException) {
            throw new AccountDaoException("failed to getCurrent balance", dataAccessException);
        }
    }

    @Override
    public Account getAccountDetail(final int accountNumber) throws AccountDaoException {
        final SqlParameterSource namedParameters = new MapSqlParameterSource("accNumber", accountNumber);
        try {
            return namedParameterJdbcTemplate.queryForObject(accountDetail, namedParameters, new AccountMapper());
        }
        catch (final DataAccessException dataAccessException) {
            throw new AccountDaoException("failed to getAccountDetails ", dataAccessException);
        }
    }

    @Override
    public synchronized double withDrawAmount(final int accountNumber, final double amount) throws AccountDaoException {
        final MapSqlParameterSource paramSourceWithDraw = toUpdateBalanceParamSource(accountNumber, amount);
        try {
            return  namedParameterJdbcTemplate.update(getWithDraw, paramSourceWithDraw);
        }
        catch (final DataAccessException dataAccessException) {
            throw new AccountDaoException("failed to withDrawAmount ", dataAccessException);
        }
    }

    @Override
    public int getAccountTypeId(final String accountType) throws AccountDaoException {
        try {
            final SqlParameterSource getAccTypeId = new MapSqlParameterSource("type", accountType);
            return namedParameterJdbcTemplate.queryForObject(getGetAccTypeId, getAccTypeId, Integer.class);
        }
        catch (final DataAccessException dataAccessException) {
            throw new AccountDaoException("Failed to get accountTypeID ", dataAccessException);
        }
    }

    /**
     * method  map sql-parameter for add account.
     *
     * @param account instance of class {@link Account}
     * @param accountTypeId accountTypeId
     * @return paramSource
     */
    private MapSqlParameterSource mapParameterSource(final Account account, final int accountTypeId) {
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("accTypeId", accountTypeId);
        paramSource.addValue("balance", account.getBalance());
        paramSource.addValue("customerId", account.getCustomerId());
        return paramSource;
    }

    /**
     * sql-parameter for method withDrawAmount() and depositeAmount().
     *
     * @param accNumber accountNumber
     * @param amount amount
     * @return paramSource instance of {@link MapSqlParameterSource}
     */
    private MapSqlParameterSource toUpdateBalanceParamSource(final int accNumber, final double amount) {
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("amount", amount);
        paramSource.addValue("accNumber", accNumber);
        return paramSource;
    }
}
