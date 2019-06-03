package com.filipbudisa.kusur.exception;

public class DataException extends Exception {
	private String body;

	public DataException(String message, String body){
		super(message);
		this.body = body;
	}

	public String getBody(){
		return body;
	}
}
