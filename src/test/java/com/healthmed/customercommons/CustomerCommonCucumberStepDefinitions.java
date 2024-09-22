package com.healthmed.customercommons;

import com.healthmed.domain.dtos.doctor.DoctorDTO;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerCommonCucumberStepDefinitions {

    @Autowired
    private CustomerHttpClient customerHttpClient;

    private DoctorDTO doctorDTO;

    private HttpStatus httpStatus;

    @Given("I have the customer's data")
    public void iHaveTheCustomersData() {
        doctorDTO = new DoctorDTO();
        doctorDTO.setName("João");
        doctorDTO.setCpf("12345678900");
    }

    @Given("I have a customer registered with CPF {string}")
    public void iHaveACustomerRegisteredWithCpf(final String cpf) {
        doctorDTO = new DoctorDTO();
        doctorDTO.setName("João");
        doctorDTO.setCpf(cpf);
        customerHttpClient.registerCustomer(doctorDTO);
    }

    @When("I send a request to register the customer")
    public void iSendARequestToRegisterTheCustomer() {
        httpStatus = null;
        httpStatus = customerHttpClient.registerCustomer(doctorDTO);
    }

    @Then("the customer is successfully registered")
    public void theCustomerIsSuccessfullyRegistered() {
        assertEquals(HttpStatus.OK, httpStatus);
    }

    @When("I send a request to retrieve the customer by CPF {string}")
    public void iSendARequestToRetrieveTheCustomerByCpf(final String cpf) {
        doctorDTO = customerHttpClient.getCustomer(cpf);
    }

    @Then("I receive the customer's data")
    public void iReceiveTheCustomersData() {
        assertNotNull(doctorDTO);
    }

    @And("the customer's name is {string}")
    public void theCustomersNameIs(String name) {
        assertEquals(name, doctorDTO.getName());
    }
}
