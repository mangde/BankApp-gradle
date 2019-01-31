/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */

package com.nuance.him.dao.atm;

import com.nuance.him.dao.daoexception.AtmDaoException;
import com.nuance.him.model.atm.AtmDetail;
import java.util.List;

/**
 * AtmDao interface.
 */
public interface SpringAtmDao {
    /**
     * Issue Atm to customer.
     * @param atmDetail instance of AtmDetail
     * @return atmDetails
     * @throws AtmDaoException exception if fail to issue.
     */
    AtmDetail issueAtmCard(AtmDetail atmDetail)  throws AtmDaoException;

    /**
     * check  accNumber already taken atm.
     * @param accNumber account number
     * @return atmNumber
     * @throws AtmDaoException exception .
     */
    AtmDetail isAlreadyAtmTaken(int accNumber)throws AtmDaoException;

    /**
     * Display all atm detail.
     * @return list of atm record
     * @throws AtmDaoException exception
     */
    List<AtmDetail> displayAllAtmDetail()throws AtmDaoException;
}
