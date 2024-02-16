package com.example.mocriservices.microservices;

import com.example.accessingdatajpa.Customer;
import com.example.accessingdatajpa.CustomerRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("ci")
public class MainControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    private void cleanDatabase(){
        customerRepository.deleteAll();
    }

    @Test
    public void demoAll_whenDbIsEmpty_returnEmptyArray() {
        assertThat(this.restTemplate
                .getForObject(getDemoAllUrl(),String.class))
                .contains("[]");
    }

    @Test
    @Order(1)
    public void demoAll_whenThereIsOneUser_returnThatUser(){
        customerRepository.save(new Customer("Nathan", "Franco"));

        assertThat(this.restTemplate
                .getForObject(getDemoAllUrl(),String.class))
                .contains("[{\"id\":1,\"firstName\":\"Nathan\",\"lastName\":\"Franco\"}]");
    }

    @Test
    public void demoAll_whenThereIsTwoUser_returnTwoUsers() throws JSONException {
        customerRepository.save(new Customer("First 1", "Last 1"));
        customerRepository.save(new Customer("First 2", "Last 2"));

        JSONArray jsonResponse = new JSONArray(this.restTemplate.getForObject(getDemoAllUrl(), String.class));
        assertThat(jsonResponse.length()).isEqualTo(2);
        assertThat(jsonResponse.getJSONObject(0).get("firstName")).isEqualTo("First 1");
        assertThat(jsonResponse.getJSONObject(1).get("firstName")).isEqualTo("First 2");
    }

    @Test
    public void demoAdd_whenAddIsCalled_databaseIsPopulated(){
        ResponseEntity<String> httpResponse = this.restTemplate.postForEntity(getDemoAddUrl(), null, String.class);
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(httpResponse.getBody()).isEqualTo("Saved");
        assertThat(customerRepository.count()).isEqualTo(1);
        assertThat(customerRepository.findByLastName("Franco").size()).isEqualTo(1);
    }

    private String getDemoAddUrl() {
        return "http://localhost:" + port + "/demo/add?firstName=Nathan&lastName=Franco";
    }

    private String getDemoAllUrl() {
        return "http://localhost:" + port + "/demo/all";
    }

}