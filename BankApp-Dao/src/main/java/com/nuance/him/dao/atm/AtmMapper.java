/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.dao.atm;

import org.springframework.jdbc.core.RowMapper;
import com.nuance.him.model.atm.AtmDetail;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AtmMapper class.
 */
public class AtmMapper implements RowMapper<AtmDetail> {

    /**
     * Maps table row to the ATM object.
     *
     * @param resultSet result set
     * @param i current row number
     * @return atmDetail
     * @throws SQLException SQLException
     */
    @Override
    public AtmDetail mapRow(final ResultSet resultSet, final int i) throws SQLException {
        final AtmDetail atmDetail = new AtmDetail(resultSet.getInt("atmNumber"), resultSet.getInt("accNumber"), resultSet.getInt("cvvNumber"),resultSet.getString("cardType"));
        atmDetail.setActivateDate(resultSet.getDate("activateDate"));
        atmDetail.setValidFrom(resultSet.getDate("validFrom"));
        atmDetail.setValidUpTo(resultSet.getDate("validUpTo"));
        atmDetail.setAtmPin(resultSet.getInt("atmPin"));
        return atmDetail;
    }
}
