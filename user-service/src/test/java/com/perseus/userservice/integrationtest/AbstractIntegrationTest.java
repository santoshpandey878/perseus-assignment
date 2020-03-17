package com.perseus.userservice.integrationtest;

import org.junit.ClassRule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;

@ContextConfiguration(initializers = {AbstractIntegrationTest.Initializer.class})
public class AbstractIntegrationTest {

	@ClassRule
	public static PostgreSQLContainer postgres = new PostgreSQLContainer<>("postgres")
													.withInitScript("schema.sql")
													.withDatabaseName("postgres")
													.withUsername("postgres")
													.withPassword("docker");

	@Value("http://localhost:${local.server.port}/userservice")
	protected String baseUrl;

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
					"spring.datasource.url=" + postgres.getJdbcUrl(),
					"spring.datasource.username=" + postgres.getUsername(),
					"spring.datasource.password=" + postgres.getPassword()
					).applyTo(configurableApplicationContext.getEnvironment());
		}
	}
}