package com.perseus.userservice.core.exceptions;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * A sample class for adding error information in the response
 */
public class RestErrorInfo {

	private LocalDateTime timeStamp;
	private Integer code;
	private String status;
	private List<String> errorMessages = new LinkedList<>();

	public RestErrorInfo(HttpStatus status, String errorMessage) {
		this.timeStamp = LocalDateTime.now();
		this.code = status.value();
		this.status = status.name();
		this.errorMessages.add(errorMessage);
	}

	public RestErrorInfo(HttpStatus status, List<String> errorMessages) {
		this.timeStamp = LocalDateTime.now();
		this.code = status.value();
		this.status = status.name();
		this.errorMessages = errorMessages;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public List<String> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}
