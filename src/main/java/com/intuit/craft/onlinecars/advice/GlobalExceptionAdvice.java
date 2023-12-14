package com.intuit.craft.onlinecars.advice;

import com.intuit.craft.onlinecars.dto.response.ErrorResponseDTO;
import com.intuit.craft.onlinecars.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestControllerAdvice
public class GlobalExceptionAdvice  extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        (error) -> {
                            String fieldName;
                            try {
                                fieldName = ((FieldError) error).getField();
                            } catch (Exception e) {
                                fieldName = error.getCode();
                            }
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });

        String errorString = CollectionUtils.isEmpty(errors) ? "Validation error occurred" : errors.toString();
        return new ResponseEntity<>(getErrorResponseDTO(HttpStatus.BAD_REQUEST,
                new Exception(errorString), ExceptionCodes.V101, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<ErrorResponseDTO> handleValidationException(ServiceException exception, WebRequest request) {
        return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, exception, exception.getExceptionCode(), null);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException exception, WebRequest request) {
        return getErrorResponseResponseEntity(HttpStatus.BAD_REQUEST, (Exception) exception.getCause().getCause(), ExceptionCodes.V101, null);
    }

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<ErrorResponseDTO> handleServiceException(ServiceException exception, WebRequest request) {
        return getErrorResponseResponseEntity(HttpStatus.EXPECTATION_FAILED, exception, exception.getExceptionCode(), null);
    }

    private ResponseEntity<ErrorResponseDTO> getErrorResponseResponseEntity(HttpStatus errorCode, Exception exception,
                                                                            ExceptionCode exceptionCode, Map<String, Object> details) {
        return new ResponseEntity<>(getErrorResponseDTO(errorCode, exception, exceptionCode, details), errorCode);
    }

    private ErrorResponseDTO getErrorResponseDTO(HttpStatus errorCode, Exception exception,
                                                 ExceptionCode exceptionCode, Map<String, Object> details) {
        String errorId = UUID.randomUUID().toString();
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .exceptionId(errorId)
                .exception(exception.getMessage())
                .exceptionCode(exceptionCode.getCode())
                .details(details)
                .application("online-car")
                .build();
        errorResponseDTO.setStatusCode(errorCode.value());
        errorResponseDTO.setStatusMessage("Error Occurred");
        return errorResponseDTO;
    }

}
