package com.reis.demo.park.api.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class SpringJpaAuditingConfig implements AuditorAware<String>{

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
    
}
