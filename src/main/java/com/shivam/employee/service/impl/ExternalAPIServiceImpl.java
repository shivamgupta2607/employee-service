package com.shivam.employee.service.impl;

import com.shivam.employee.dto.request.UserRequest;
import com.shivam.employee.exception.ServiceException;
import com.shivam.employee.service.ExternalAPIService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Service
@Log4j2
public class ExternalAPIServiceImpl implements ExternalAPIService {

    private RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(100)).setReadTimeout(Duration.ofMillis(4 * 1000)).build();

    @Override
    public String createUserFromExternalAPI(final UserRequest userRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(userRequest, httpHeaders);

        try {
            final ResponseEntity<String> testTeamEntity = this.restTemplate.exchange("http://localhost:8081/v1/team/1", HttpMethod.GET, httpEntity, String.class);
            String body = testTeamEntity.getBody();
            log.info("Response is {}", body);
            return body;
        } catch (final HttpStatusCodeException e) {
            throw new ServiceException(String.format("Error while creating user, response body is ", e.getResponseBodyAsString()), e.getStatusCode());
        }
    }
}
