/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.dao.customer;

import org.springframework.jdbc.core.RowMapper;
import com.nuance.him.model.customer.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper to display the table rows mapping them to the objects.
 */
public class CustomerMapper implements RowMapper<Customer> {

    /**
     * Maps table row to the Customer object.
     *
     * @param resultSet result set
     * @param i current row number
     * @return customer
     * @throws SQLException SQLException
     */
    @Override
    public Customer mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final Customer customer = new Customer(resultSet.getString("name"), resultSet.getLong("phone"),
            resultSet.getString("address1"), resultSet.getString("city"));
        customer.setId(resultSet.getInt("customerId"));
        return customer;
    }
}
