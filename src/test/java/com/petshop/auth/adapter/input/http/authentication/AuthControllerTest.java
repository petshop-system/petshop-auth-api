package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ActiveProfiles("test")
@WebMvcTest(AuthController.class)
@EnableWebMvc
@ExtendWith(SpringExtension.class)
public class AuthControllerTest {

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
                .content(body);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

}
