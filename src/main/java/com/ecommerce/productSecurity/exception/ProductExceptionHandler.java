package com.ecommerce.productSecurity.exception;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

@ControllerAdvice
public class ProductExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataNotFoundExceptions(
    		DataIntegrityViolationException e
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 404

        return new ResponseEntity<ErrorResponse>(
            new ErrorResponse(
              status, 
              e.getMessage(),
          null
            ),
            status
        );
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<ErrorResponse> handleCustomDataNotFoundExceptions(
    		MissingServletRequestPartException e
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 404

        return new ResponseEntity<ErrorResponse>(
            new ErrorResponse(
              status, 
              e.getMessage(),
          null
            ),
            status
        );
    }
}