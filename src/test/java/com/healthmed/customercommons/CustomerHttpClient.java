package com.healthmed.customercommons;

import com.healthmed.domain.dtos.doctor.DoctorDTO;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static io.cucumber.spring.CucumberTestContext.SCOPE_CUCUMBER_GLUE;

@Component
@Scope(SCOPE_CUCUMBER_GLUE)
public class CustomerHttpClient {

    private final String SERVER_URL = "http://localhost";
    private final String CUSTOMERS_ENDPOINT = "/customers";

    @LocalServerPort
    private int port;
    private final RestTemplate restTemplate = new RestTemplate();


    private String customersEndpoint() {
        return SERVER_URL + ":" + port + CUSTOMERS_ENDPOINT;
    }

    public DoctorDTO getCustomer(String cpf) {
        return restTemplate.getForEntity(customersEndpoint() + "/" + cpf, DoctorDTO.class).getBody();
    }

    public HttpStatus registerCustomer(DoctorDTO doctorDTO) {
        return restTemplate.postForEntity(customersEndpoint(), doctorDTO, Void.class).getStatusCode();
    }

}
