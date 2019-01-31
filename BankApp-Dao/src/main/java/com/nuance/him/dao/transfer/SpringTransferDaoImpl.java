/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.transfer;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.nuance.him.dao.daoexception.TransferDaoException;
import com.nuance.him.model.transfer.TransferAmount;
import java.util.Date;
import java.util.List;

/**
 * implements {@link SpringTransferDao}.
 */
public class SpringTransferDaoImpl implements SpringTransferDao {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final String getTransferAmount;
    private final String getTransactionHistory;

    /**
     * constructor of SpringTransferDaoImpl.
     *
     * @param namedParameterJdbcTemplate instance of {@link NamedParameterJdbcTemplate}
     * @param getTransferAmount query for transfer amount
     * @param getTransactionHistory query for getTransaction Details
     */
    public SpringTransferDaoImpl(final NamedParameterJdbcTemplate namedParameterJdbcTemplate, final String getTransferAmount, final String getTransactionHistory) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.getTransferAmount = getTransferAmount;
        this.getTransactionHistory = getTransactionHistory;
    }

    @Override
    public int transferAmount(final TransferAmount transferAmount) throws TransferDaoException {
        try {
            final KeyHolder holder = new GeneratedKeyHolder();
            final MapSqlParameterSource paramSource = mapParameterSource(transferAmount);
            namedParameterJdbcTemplate.update(getTransferAmount, paramSource, holder);
            return holder.getKey().intValue();
        }
        catch (final DataAccessException dataAccessException) {
            throw new TransferDaoException("Failed to transfer amount", dataAccessException);
        }
    }

    @Override
    public List<TransferAmount> getTransactionHistory(final int accountNumber) throws TransferDaoException {
        try {
            final SqlParameterSource accNumber = new MapSqlParameterSource("accNumber", accountNumber);
            return namedParameterJdbcTemplate.query(getTransactionHistory, accNumber, new TransactionMapper());
        }
        catch (final DataAccessException dataAccessException) {
            throw new TransferDaoException("Failed to display TransferAmount details", dataAccessException);
        }
    }

    /**
     * method to map sqlParameter.
     *
     * @param transferAmount instance of class {@link TransferAmount}
     * @return instance of {@link MapSqlParameterSource}
     */
    private MapSqlParameterSource mapParameterSource(final TransferAmount transferAmount) {
        final Date date = new Date();
        final Object param = new java.sql.Timestamp(date.getTime());
        final MapSqlParameterSource paramSource = new MapSqlParameterSource();
        paramSource.addValue("dateTime", param);
        paramSource.addValue("amount", transferAmount.getAmount());
        paramSource.addValue("accFrom", transferAmount.getAccIdFrom());
        paramSource.addValue("accTo", transferAmount.getAccIdTo());
        paramSource.addValue("description", transferAmount.getDescription());
        return paramSource;
    }
}
