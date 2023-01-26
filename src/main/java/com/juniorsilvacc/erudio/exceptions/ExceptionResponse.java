package com.juniorsilvacc.erudio.exceptions;

import java.io.Serializable;
import java.util.Date;


public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date timestamp;
	private String message;
	private String path;
	

	public ExceptionResponse(Date timestamp, String message, String path) {
		this.timestamp = timestamp;
		this.message = message;
		this.path = path;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

}
