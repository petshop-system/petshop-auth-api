package com.petshop.auth.adapter.input.http.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petshop.auth.PetshopAuthApiApplication;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationCodeValidationProxyDomain;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyService;
import com.petshop.auth.adapter.input.proxy.authentication.AuthenticationProxyServiceImpl;
import com.petshop.auth.application.port.input.AuthenticationUsercase;
import com.petshop.auth.application.service.AuthenticationService;
import com.petshop.auth.configuration.*;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@SpringBootTest(classes = { PetshopAuthApiApplication.class, AuthController.class,
        AuthenticationConfiguration.class, AuthenticationProxyService.class, AuthenticationUsercase.class,
        AuthenticationConfigurationTest.class,
        CacheConfigurationTest.class,
        AuthorizationConfiguration.class,
        AccessTokenConfiguration.class, AccessTokenConfigurationTest.class,
        ProfileAccessConfiguration.class, ProfileAccessConfigurationTest.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    // https://www.baeldung.com/spring-beans-integration-test-override
    // https://stackoverflow.com/questions/53723303/springboot-beandefinitionoverrideexception-invalid-bean-definition

    @Autowired
    MockMvc mockMvc;

    @MockBean//(name = AuthenticationConfiguration.AUTHENTICATION_PROXY_SERVICE)
    private AuthenticationProxyService authenticationProxyServiceMock;

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

    @Test
    public void validateCodeValidationTest () throws Exception {

        var reference = "my-test-reference";
        var code = "12345";

        AuthenticationCodeValidationRequest codeValidationRequest =
                new AuthenticationCodeValidationRequest(reference, code);

        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(codeValidationRequest);

        Mockito.when(authenticationProxyServiceMock.getCodeValidation(any(), anyInt()))
                .thenReturn(body);

        Mockito.when(authenticationProxyServiceMock.validateCodeValidation(any(), any(), any(), any()))
                .then(i -> {
                    var as = new AuthenticationService(null, null);
                    var aps = new AuthenticationProxyServiceImpl(as, null, null);
                    aps.validateCodeValidation((String) i.getArguments()[0],
                            (String) i.getArguments()[1],
                            (String) i.getArguments()[2],
                            (String) i.getArguments()[3]);
                    return null;
                });

        String url = CONTROLLER_PATH + "/validate-code-validation/";
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .header(AuthController.Request_ID_Header, "my-request-id")
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .content(body);

        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

}

