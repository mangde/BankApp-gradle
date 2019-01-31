/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.account;

import org.springframework.jdbc.core.RowMapper;
import com.nuance.him.model.account.Account;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper to display the table rows mapping them to the objects.
 */
public class AccountMapper implements RowMapper<Account> {

    /**
     * Maps table row to the Account object.
     *
     * @param resultSet result set
     * @param i current row number
     * @return account
     * @throws SQLException SQLException
     */
    @Override
    public Account mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final Account account = new Account(resultSet.getString("accTypeId"), resultSet.getDouble("balance"), resultSet.getInt("customerId"));
        account.setAccountNo(resultSet.getInt("accNumber"));
        return account;
    }
}
