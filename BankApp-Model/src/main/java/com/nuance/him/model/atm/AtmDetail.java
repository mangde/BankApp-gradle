/*
 *
 *  COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 *  Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 * /
 *
 */
package com.nuance.him.model.atm;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * AtmDetail model class.
 */
public class AtmDetail {

    private @NotNull int atmNumber;
    private @NotNull int accNumber;
    private int atmPin;
    private @NotNull int cvvNumber;
    private Date activateDate;
    private Date validFrom;
    private Date validUpTo;
    private @NotNull String atmType;

    /**
     * initialize atm  variable.
     *
     * @param atmNumber atmNumber
     * @param accNumber accountNumber
     * @param cvvNumber atm cvvNumber
     * @param atmType atmType eg.master/visa
     */
    public AtmDetail(final int atmNumber, final int accNumber, final int cvvNumber, final String atmType) {
        this.atmNumber = atmNumber;
        this.accNumber = accNumber;
        this.atmType = atmType;
        this.cvvNumber = cvvNumber;
    }

    /**
     * default constructor.
     */
    public AtmDetail() {
    }

    /**
     * getAtmNumber.
     *
     * @return AtmNumber
     */
    public int getAtmNumber() {
        return atmNumber;
    }

    /**
     * getAccountNumber.
     *
     * @return AccountNumber
     */
    public int getAccNumber() {
        return accNumber;
    }

    /**
     * getAtmPin.
     *
     * @return AtmPin
     */
    public int getAtmPin() {
        return atmPin;
    }

    /**
     * getCvvNumber.
     *
     * @return cvvNumber
     */
    public int getCvvNumber() {
        return cvvNumber;
    }

    /**
     * get Atm Activation date.
     *
     * @return ActivationDate
     */
    public Date getActivateDate() {
        return activateDate;
    }

    /**
     * get Atm Valid from date.
     *
     * @return validFromDate
     */
    public Date getValidFrom() {
        return validFrom;
    }

    /**
     * get validUpTo Date.
     *
     * @return validUpTo
     */
    public Date getValidUpTo() {
        return validUpTo;
    }

    /**
     * getAtmType.
     *
     * @return AtmType
     */
    public String getAtmType() {
        return atmType;
    }

    /**
     * set Atm activate date.
     *
     * @param activateDate date
     */
    public void setActivateDate(final java.sql.Date activateDate) {
        this.activateDate = activateDate;
    }

    /**
     * set atm validFromDate.
     *
     * @param validFrom date
     */
    public void setValidFrom(final java.sql.Date validFrom) {
        this.validFrom = validFrom;
    }

    /**
     * set expiry date.
     *
     * @param validUpTo date
     */
    public void setValidUpTo(final java.sql.Date validUpTo) {
        this.validUpTo = validUpTo;
    }

    /**
     * set atmPin.
     *
     * @param atmPin pin
     */
    public void setAtmPin(final int atmPin) {
        this.atmPin = atmPin;
    }

    @Override
    public String toString() {
        return "AtmDetail{" +
            "atmNumber=" + atmNumber +
            ", accNumber=" + accNumber +
            ", atmPin=" + atmPin +
            ", cvvNumber=" + cvvNumber +
            ", activateDate=" + activateDate +
            ", validFrom=" + validFrom +
            ", validUpTo=" + validUpTo +
            ", atmType='" + atmType + '\'' +
            '}';
    }
}
