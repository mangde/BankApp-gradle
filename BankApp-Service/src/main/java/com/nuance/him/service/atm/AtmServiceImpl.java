/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.service.atm;

import com.nuance.him.dao.atm.SpringAtmDao;
import com.nuance.him.dao.daoexception.AtmDaoException;
import com.nuance.him.model.atm.AtmDetail;
import com.nuance.him.service.account.AccountService;
import com.nuance.him.service.exception.AccountServiceException;
import com.nuance.him.service.exception.AtmServiceException;
import java.util.List;

/**
 * implementation of {@link AtmService} interface.
 */
public class AtmServiceImpl implements AtmService {

    private final SpringAtmDao springAtmDao;
    private final AccountService accountService;

    /**
     * constructor of class {@link AtmServiceImpl}.
     *
     * @param springAtmDao instance of {@link SpringAtmDao}
     * @param accountService instance of {@link AccountService}
     */
    public AtmServiceImpl(final SpringAtmDao springAtmDao, final AccountService accountService) {
        this.springAtmDao = springAtmDao;
        this.accountService = accountService;
    }

    @Override
    public AtmDetail issueAtmCard(final AtmDetail atmDetail) throws AtmServiceException {
        try {
            accountService.getAccountDetail(atmDetail.getAccNumber());
        }
        catch (final AccountServiceException accountServiceException) {
            throw new AtmServiceException("Account NOt Found", accountServiceException);
        }
        try {
            return springAtmDao.issueAtmCard(atmDetail);
        }
        catch (
            final AtmDaoException atmDaoException) {
            throw new AtmServiceException("exception in AtmService", atmDaoException);
        }
    }

    @Override
    public AtmDetail isAlreadyAtmTaken(final int accNumber) throws AtmServiceException {
        try {
            return springAtmDao.isAlreadyAtmTaken(accNumber);
        }
        catch (final AtmDaoException atmDaoException) {
            throw new AtmServiceException("exception to check atm", atmDaoException);
        }
    }

    @Override
    public List<AtmDetail> displayAllAtmDetail() throws AtmServiceException {
        try {
            return springAtmDao.displayAllAtmDetail();
        }
        catch (final AtmDaoException atmDaoException) {
            throw new AtmServiceException("exception to check atm", atmDaoException);
        }
    }
}
