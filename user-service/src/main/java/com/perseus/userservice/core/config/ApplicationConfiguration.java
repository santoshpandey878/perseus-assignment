package com.perseus.userservice.core.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class responsible to handle application level configuration
 *
 */
@Configuration
public class ApplicationConfiguration {

	/**
	 * ModelMapper bean used for DTO conversion
	 * @return
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	} 

}
