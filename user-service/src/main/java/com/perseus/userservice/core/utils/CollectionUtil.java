package com.perseus.userservice.core.utils;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

/**
 * Utilty class for classes which is a part of collection framework
 */
public class CollectionUtil extends CollectionUtils{

	/**
	 * Return false if the supplied Collection is not null or not empty. Otherwise, return true.
	 * @param collection
	 * @return
	 */
	public static <T> boolean isNotEmpty(Collection<T> collection){
		return !isEmpty(collection);
	}

}
