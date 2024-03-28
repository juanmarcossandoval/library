package com.bibliotek.library.controllers;

import com.bibliotek.library.controllers.responses.ResponseErrorDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bibliotek.library.exceptions.BadRequestException;
import com.bibliotek.library.exceptions.NotFoundException;

@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ResponseErrorDto handleBadRequestException(Exception e) {
		return new ResponseErrorDto(400,e.getMessage()); 
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseErrorDto handleNotFoundException(Exception e) { 
		return new ResponseErrorDto(404,e.getMessage()); 
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String msg = generateBindErrorMessage(ex);
		ResponseErrorDto errorDto = new ResponseErrorDto(404,msg);
		return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
	}

	private String generateBindErrorMessage(MethodArgumentNotValidException ex){
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (ObjectError oe : ex.getBindingResult().getAllErrors()){
			index++;
			sb.append("%d- ".formatted(index)).append(oe.getDefaultMessage()).append(". ");
		}
		return sb.toString();
	}
}
