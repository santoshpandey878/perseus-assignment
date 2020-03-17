package com.perseus.userservice.core.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Utility class responsible for DTO/Entity conversion
 */
@Component
public class DtoConverter {

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * Method to convert an entity to DTO or vice-versa
	 * @param obj
	 * @param resource
	 * @return
	 */
	public <T> T convert(Object obj, final Class<T> resource) {
		return modelMapper.map(obj, resource);
	}

}