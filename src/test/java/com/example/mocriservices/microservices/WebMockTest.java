package com.example.mocriservices.microservices;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.accessingdatajpa.Customer;
import com.example.accessingdatajpa.CustomerRepository;
import com.example.accessingdatajpa.MainController;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;

@WebMvcTest(MainController.class)
public class WebMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerRepository service;

    @Test
    public void demoAllShouldReturnAnEmptyArray() throws Exception {
        when(service.findAll()).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/demo/all")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    public void when_thereAreTwoCustomer_TotalIsTwo() throws Exception {
        ArrayList<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Nathan", "Franco"));
        customers.add(new Customer("Gustavo", "Franco"));
        when(service.findByLastName("Franco")).thenReturn(customers);

        MvcResult mvcResult = this.mockMvc.perform(get("/demo/find").queryParam("lastName", "Franco"))
//                .andDo(print()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(2))
                .andExpect(jsonPath("$.userList", hasSize(2)))
                .andExpect(jsonPath("$.userList[0].lastName", is("Franco")))
                .andExpect(jsonPath("$.userList[0].lastName").value("Franco"))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertThat(jsonObject.getInt("total")).isEqualTo(2);
    }
}