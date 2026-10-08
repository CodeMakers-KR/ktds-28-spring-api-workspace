package com.ktdsuniversity.edu.commons.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.ktdsuniversity.edu.commons.vo.PaginationVO;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ApiResponseV2<T> {

	private int httpStatusCode;
	private String httpStatusMessage;
	
	private T body;
	private String error;
	
	private PaginationVO paginate;
	private Object search;
	
	private Map<String, List<String>> validations;
	
	public static <T> ApiResponseV2<T> OK(T t) {
		ApiResponseV2<T> result = new ApiResponseV2<>();
		result.setHttpStatusCode(HttpStatus.OK.value());
		result.setHttpStatusMessage(HttpStatus.OK.getReasonPhrase());
		result.setBody(t);
		
		return result;
	}
	
	public static <T> ApiResponseV2<T> CREATED(T t) {
		ApiResponseV2<T> result = new ApiResponseV2<>();
		result.setHttpStatusCode(HttpStatus.CREATED.value());
		result.setHttpStatusMessage(HttpStatus.CREATED.getReasonPhrase());
		result.setBody(t);
		
		return result;
	}
	
	public static <T> ApiResponseV2<T> ERROR(String message) {
		ApiResponseV2<T> result = new ApiResponseV2<>();
		result.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		result.setHttpStatusMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
		result.setError(message);
		
		return result;
	}
	
	public static <T> ApiResponseV2<T> FORBIDDEN(String message) {
		ApiResponseV2<T> result = new ApiResponseV2<>();
		result.setHttpStatusCode(HttpStatus.FORBIDDEN.value());
		result.setHttpStatusMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
		result.setError(message);
		
		return result;
	}
	
	
	public static <T> ApiResponseV2<T> BAD_REQUEST(List<? extends MessageSourceResolvable> errors) {
		
		ApiResponseV2<T> result = new ApiResponseV2<>();
		result.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		result.setHttpStatusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase());
		
		result.validations = new HashMap<>();
		errors.forEach(error -> {
			String fieldName = null;
			if (error instanceof FieldError fieldError) {
				fieldName = fieldError.getField();
			} else {
				DefaultMessageSourceResolvable paramError = (DefaultMessageSourceResolvable) error.getArguments()[0];
				fieldName = paramError.getDefaultMessage();
			}
			if ( ! result.validations.containsKey(fieldName) ) {
				List<String> errorMessages = new ArrayList<>();
				result.validations.put(fieldName, errorMessages);
			}
			result.validations.get(fieldName).add(error.getDefaultMessage());
		});
		return result;
	}
	
}
