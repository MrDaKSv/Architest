package chnu._3.myproject.config;/*
  @author Bogdan
  @project Architest
  @class AuditorAware
  @version 1.0.0
  @since 30.04.2025 - 22.13
*/

import java.util.Optional;

public class AuditorAwareImpl implements org.springframework.data.domain.AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(System.getProperty("user.name"));
    }
}
