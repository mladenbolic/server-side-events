package io.sixhours.serversideevents.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * Web configuration.
 *
 * <p>Used to configure CORS mappings.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebFluxConfigurer {

  /**
   * Adds CORS configuration.
   *
   * @param registry cors registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedHeaders("*")
        .allowedMethods("*")
        .maxAge(3600);
  }
}