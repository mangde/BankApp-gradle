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
import com.nuance.him.model.transfer.TransferAmount;
import com.nuance.him.service.transfer.TransferAmountService;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@link TransferAmountControllerTest} test class.
 */
@WebAppConfiguration
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = TestControllerConfig.class)
public class TransferAmountControllerTest extends AbstractTestNGSpringContextTests {

    private static final String BASE_URL = "baseURL";
    private static final String TRANSFER_AMOUNT = "Transaction.transferAmount";
    private static final String TRANSACTION_HISTORY = "Transaction.history";
    private static final double AMOUNT = 500;
    private static final int FROM_ACC = 1;
    private static final int TO_ACC = 3;
    private static final String DESCRIPTION = "Loan";
    @Value("${" + TRANSFER_AMOUNT + "}")
    private String getTransferAmount;
    @Value("${" + BASE_URL + "}")
    private String bankURL;
    @Value("${" + TRANSACTION_HISTORY + "}")
    private String getTransactionHistory;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Mock
    private TransferAmountService transferAmountService;
    private MockMvc mockMvc;
    private TransferAmount transferAmount;
    private String requestJson;

    /**
     * Initial Setup.
     * @throws Exception jsonException
     */
    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        transferAmount = new TransferAmount(FROM_ACC, TO_ACC, AMOUNT, DESCRIPTION);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        final ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        requestJson = ow.writeValueAsString(transferAmount);
    }

    /**
     * Test Transfer Amount_Method controller.
     *
     * @throws Exception exception
     */
    @Test
    public void testTransferAmount() throws Exception {
        when(transferAmountService.transferAmount(any(TransferAmount.class))).thenReturn(1);
        mockMvc.perform(MockMvcRequestBuilders.post(bankURL + getTransferAmount)
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson)).andExpect(status().isCreated());
    }

    /**
     * test method getTransactionHistory.
     *
     * @throws Exception exception
     */
    @Test
    public void testTransactionHistory() throws Exception {
        when(transferAmountService.getTransactionHistory(anyInt())).thenReturn(anyListOf(TransferAmount.class));
        mockMvc.perform(MockMvcRequestBuilders.get(bankURL + getTransactionHistory)
            .param("accNumber","1")).andExpect(status().isOk());
    }
}
