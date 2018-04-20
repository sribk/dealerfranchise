package com.sri.dealerfranchise.error;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());


    public RestResponseEntityExceptionHandler() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        logger.warn("MethodArgumentNotValid: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(status, e, e.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        logger.warn("HttpMessageNotReadable: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(status, e), headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e,
                                                                        WebRequest request) {
        logger.warn("DataIntegrityViolationException: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(HttpStatus.BAD_REQUEST, e), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException e,
                                                                WebRequest request) {
        logger.warn("EntityNotFoundException: " + e.getMessage(), e);
        return handleExceptionInternal(e, message(HttpStatus.NOT_FOUND, e), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    private ApiError message(HttpStatus status, Exception e) {
        return new ApiError(status.value(),
                ExceptionUtils.getRootCauseMessage(e),
                e.getMessage());
    }

    private ApiError message(HttpStatus status, Exception e, BindingResult result) {
        return new ApiError(status.value(),
                ExceptionUtils.getRootCauseMessage(e),
                e.getMessage(),
                ValidationUtil.fromBindingErrors(result));
    }

}
