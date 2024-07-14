package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.PetshopAuthApiApplication;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyService;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.configuration.AuthenticationConfiguration;
import com.petshop.auth.configuration.AuthenticationConfigurationTest;
import com.petshop.auth.configuration.CacheConfigurationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = { PetshopAuthApiApplication.class, AuthenticationConfiguration.class,
        AuthController.class, AuthenticationProxyService.class, AuthenticationUsercase.class,
        AuthenticationConfigurationTest.class, CacheConfigurationTest.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    // https://www.baeldung.com/spring-beans-integration-test-override
    // https://stackoverflow.com/questions/53723303/springboot-beandefinitionoverrideexception-invalid-bean-definition

    @Autowired
    MockMvc mockMvc;

    final String CONTROLLER_PATH = "/auth";

    @Test
    public void newCodeValidationTest () throws Exception {

        var reference = "my-test-reference";
        var digits = 5;
        AuthenticationNewCodeValidationRequest newCodeValidationRequest =
                new AuthenticationNewCodeValidationRequest(reference, digits);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(newCodeValidationRequest);

        String url = CONTROLLER_PATH + "/new-code-validation/";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .header(AuthController.Request_ID_Header, "my-request-id")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .content(body);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andReturn();
    }

}

