/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.model.account;

/**
 * AccountType Class.
 */
public class AccountType {

    private final int accTypeId;
    private final String accTypeDesc;

    /**
     * Constructor of class  AccountType.
     *
     * @param accTypeId id
     * @param accTypeDesc description
     */
    public AccountType(final int accTypeId, final String accTypeDesc) {
        this.accTypeId = accTypeId;
        this.accTypeDesc = accTypeDesc;
    }

    /**
     * get accountDescription.
     *
     * @return accountDescription
     */
    public String getAccTypeDesc() {
        return accTypeDesc;
    }

    /**
     * get accountTypeId.
     *
     * @return accountTypeId
     */
    public int getAccTypeId() {
        return accTypeId;
    }

    @Override
    public String toString() {
        return "AccountType{" +
            "accTypeId=" + accTypeId +
            ", accTypeDesc='" + accTypeDesc + '\'' +
            '}';
    }
}
