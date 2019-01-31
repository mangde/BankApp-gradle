/*
 * COPYRIGHT: Copyright (c) 2019 by Nuance Communications, Inc.
 * Warning: This product is protected by United States copyright law. Unauthorized use or duplication of this software, in whole or in part, is prohibited.
 */
package com.nuance.him.controller.test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.nuance.him.controller.customer.CustomerController;
import com.nuance.him.model.customer.Customer;
import com.nuance.him.service.customer.CustomerService;
import java.util.List;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test Class for {@link CustomerController}.
 */
@WebAppConfiguration
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = TestControllerConfig.class)
public class CustomerControllerTest extends AbstractTestNGSpringContextTests {

    private static final String BASE_URL = "baseURL";
    private static final String ADD_CUSTOMER = "Customer.addCustomer";
    private static final String SELECT_ALL_CUSTOMER = "Customer.SelectAllCustomer";
    private static final String GET_CUSTOMER_BY_ID = "Customer.getCustomerById";
    private static final String NAME = "Harsh";
    private static final String CITY = "Pune";
    private static final String ADDRESS = "Pune";
    private static final long PHONE = 9545090850L;
    private static final String CUSTOMER_ID = "1";
    @Value("${" + BASE_URL + "}")
    private String bankURL;
    /**
     * add customer query.
     */
    @Value("${" + ADD_CUSTOMER + "}")
    private String getAddCustomer;
    @Value("${" + SELECT_ALL_CUSTOMER + "}")
    private String getSelectAllCustomer;
    @Value("${" + GET_CUSTOMER_BY_ID + "}")
    private String getFindCustomer;
    @Mock
    private CustomerService customerService;
    /**
     * WebApplicationContext to TestNG cases.
     */
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private Customer customer;
    private List<Customer> customers;
    private String requestJson;

    /**
     * initial setup.
     *
     * @throws Exception jsonException.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        customer = new Customer(NAME, PHONE, ADDRESS, CITY);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(customer);
    }

    /**
     * Test {@link CustomerController {@link #ADD_CUSTOMER}}for successful adding a customer.
     *
     * @throws Exception exception
     */
    @Test
    public void testAddCustomer() throws Exception {
        when(customerService.addCustomer(any(Customer.class))).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders.post(bankURL + getAddCustomer)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)).andExpect(status().isCreated());
    }

    /**
     * customer display customer method.
     *
     * @throws Exception exception
     */
    @Test
    public void testDisplayCustomers() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(customers);
        mockMvc.perform(MockMvcRequestBuilders.get(bankURL + getSelectAllCustomer))
            .andExpect(status().isOk());
    }

    /**
     * findCustomerById method.
     *
     * @throws Exception exception
     */
    @Test
    public void testFindCustomerById() throws Exception {
        when(customerService.findCustomerById(anyInt())).thenReturn(customer);
        mockMvc.perform(MockMvcRequestBuilders.get(bankURL + getFindCustomer).param("customerId", CUSTOMER_ID))
            .andExpect(status().isOk());
    }
}
