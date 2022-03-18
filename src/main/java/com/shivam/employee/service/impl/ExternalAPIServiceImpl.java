package com.shivam.employee.service.impl;

import com.shivam.employee.dto.request.UserRequest;
import com.shivam.employee.dto.response.UserResponse;
import com.shivam.employee.exception.ServiceException;
import com.shivam.employee.service.ExternalAPIService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
@Log4j2
public class ExternalAPIServiceImpl implements ExternalAPIService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserResponse createUserFromExternalAPI(final UserRequest userRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        try {
            final ResponseEntity<UserResponse> userResponseEntity = this.restTemplate.exchange("", HttpMethod.POST, httpEntity, UserResponse.class);
            UserResponse body = userResponseEntity.getBody();
            log.info("Response is {}", body);
            return body;
        } catch (final HttpStatusCodeException e) {
            throw new ServiceException(String.format("Error while creating user, response body is ", e.getResponseBodyAsString()), e.getStatusCode());
        }
    }
}
