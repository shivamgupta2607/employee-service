package com.shivam.employee.service.impl;

import com.shivam.employee.dto.request.UserRequest;
import com.shivam.employee.dto.response.UserResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class ExternalAPIServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExternalAPIServiceImpl externalAPIService;

    @Test
    public void testCreateUserFromExternalAPI() {
        UserResponse userResponse = new UserResponse();
        userResponse.setId("1000");
        userResponse.setName("A");
        userResponse.setJob("Job1");
        userResponse.setCreatedAt("2020-10-05");

        UserRequest userRequest = new UserRequest();
        userRequest.setName("A");
        userRequest.setJob("Job1");


        ResponseEntity<UserResponse> responseEntity = ResponseEntity.ok(userResponse);
        Mockito.when(restTemplate.exchange
                (ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class),
                        ArgumentMatchers.any(), ArgumentMatchers.<Class<UserResponse>>any())).thenReturn(responseEntity);
        String userFromExternalAPI = externalAPIService.getTeamFromExternalAPI(userRequest);
        Assert.assertNotNull(userFromExternalAPI);
    }
}