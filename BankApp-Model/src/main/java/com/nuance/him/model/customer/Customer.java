/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.model.customer;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Customer Model class.
 */
public class Customer {

    private int id;
    private @NotNull @Pattern(regexp = "[a-z A-z]+", message = "Name should contain Alphabets only")    String name;
    private @NotNull@Min(10)@Max(10)long phone;
    private @NotEmpty String address;
    private @Pattern(regexp = "[a-z A-z]+", message = "City should contain Alphabets only")   String city;

    /**
     * Customer Constructor.
     *
     * @param name name of customer
     * @param phone phone
     * @param address address
     * @param city city
     */
    public Customer(final String name, final Long phone, final String address, final String city) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
    }

    /**
     * default constructor.
     */
    public Customer() {
    }

    /**
     * getCustomerId.
     *
     * @return customerId
     */
    public int getId() {
        return id;
    }

    /**
     * setCustomerId.
     *
     * @param id customerId
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * get CustomerName.
     *
     * @return CustomerName
     */
    public String getName() {
        return name;
    }

    /**
     * get phone.
     *
     * @return phone
     */
    public long getPhone() {
        return phone;
    }

    /**
     * get Customer address.
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * get city.
     *
     * @return city
     */
    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id +
            ", name='" + name + '\'' +
            ", phone=" + phone +
            ", address='" + address + '\'' +
            ", city='" + city + '\'' + '}';
    }
}
