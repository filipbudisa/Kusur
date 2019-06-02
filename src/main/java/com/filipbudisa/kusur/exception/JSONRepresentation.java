package com.filipbudisa.kusur.exception;

public class JSONRepresentation {

	private String error;

	private String message;

	public JSONRepresentation(String error, String message){
		this.error = error;
		this.message = message;
	}

	public String getError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}
