package com.innov.demo;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)

public class PaymentControllerTest {
 
    @Autowired
    private MockMvc mvc;
 
    @MockBean
    private PaymentService paymentService;
 
    @Test
    public void givenPayments_whenGetPayments_thenListPayments() throws Exception {
         
        Payment pymnt = new Payment("alex", 25.00);
        List<Payment> payments = Arrays.asList(pymnt);
     
        given(paymentService.getPayments()).willReturn(payments);
     
        mvc.perform(MockMvcRequestBuilders.get("/payments")
          .contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(jsonPath("$", hasSize(1)))
          .andExpect(jsonPath("$[0].name", is(pymnt.getName())));
    }
}