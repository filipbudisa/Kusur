package com.filipbudisa.kusur.exception;

public class NotFoundException extends Exception {

	private String entity;

	private String identifier;

	public NotFoundException(String entity, String identifier){
		this.entity = entity;
		this.identifier = identifier;
	}

	public String getEntity(){
		return entity;
	}

	public String getIdentifier(){
		return identifier;
	}
}
