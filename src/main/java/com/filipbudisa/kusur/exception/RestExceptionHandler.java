package com.filipbudisa.kusur.exception;

import org.json.JSONException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ DataException.class })
	public ResponseEntity<Object> dataException(DataException e, WebRequest request){
		return handleExceptionInternal(e, new JSONRepresentation("data", e.getMessage()),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ LogicException.class })
	public ResponseEntity<Object> logicException(LogicException e, WebRequest request){
		return handleExceptionInternal(e, new JSONRepresentation("logic", e.getMessage()),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ JSONException.class })
	public ResponseEntity<Object> JSONException(JSONException e, WebRequest request){
		return handleExceptionInternal(e, new JSONRepresentation("json", "Malformed JSON"),
				new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler({ NotFoundException.class })
	public ResponseEntity<Object> NotFoundException(NotFoundException e, WebRequest request){
		return handleExceptionInternal(e, new JSONRepresentation("not_found",
						"Couldn't find " + e.getEntity() + " with identifier " + e.getIdentifier()),
				new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
