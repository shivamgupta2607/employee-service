package com.shivam.employee.service.audit;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Audit information provider. This class mainly provides the user id of the request invoker.
 */
@Component
@Log4j2
public class AuditorAwareImpl implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            return Optional.of(-9999L);
        } catch (Exception ex) {
            log.info("In getCurrentAuditor() - Request coming from Kafka producer {}");
            return Optional.of(-1L);
        }
    }
}
