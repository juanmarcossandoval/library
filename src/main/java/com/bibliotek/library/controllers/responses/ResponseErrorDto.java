package com.bibliotek.library.controllers.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ResponseErrorDto {
	Integer code;
	String errorMsg;
}
