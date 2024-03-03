package com.bibliotek.library.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
}
