package com.bibliotek.library.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseErrorDto {
	Integer code;
	String errorMsg;
}
