/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */

package com.nuance.him.service.atm;

import com.nuance.him.model.atm.AtmDetail;
import com.nuance.him.service.exception.AtmServiceException;
import java.util.List;

/**
 * {@link AtmService} interface.
 */
public interface AtmService {

    /**
     * Issue Atm to customer.
     * @param atmDetail instance of AtmDetail
     * @return atmDetails
     * @throws AtmServiceException exception if fail to issue.
     */
    AtmDetail issueAtmCard(AtmDetail atmDetail)  throws AtmServiceException;

    /**
     * check  accNumber already taken atm.
     * @param accNumber account number
     * @return atmNumber
     * @throws AtmServiceException exception .
     */
    AtmDetail isAlreadyAtmTaken(int accNumber)throws AtmServiceException;

    /**
     * Display all atm detail.
     * @return list of atm record
     * @throws AtmServiceException exception
     */
    List<AtmDetail> displayAllAtmDetail()throws AtmServiceException;
}
