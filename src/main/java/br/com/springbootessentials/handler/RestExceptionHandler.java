package br.com.springbootessentials.handler;

import br.com.springbootessentials.error.ResourceNotFoundDetails;
import br.com.springbootessentials.error.ResourceNotFoundException;
import br.com.springbootessentials.error.ValidationErrorDetails;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> handlerResourceNotFoundException(ResourceNotFoundException rfnException){
    ResourceNotFoundDetails rnfDetails =  ResourceNotFoundDetails.Builder
        .newBuilder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.NOT_FOUND.value())
        .title("Resource Not Found")
        .detail(rfnException.getMessage())
        .developerMessage(rfnException.getClass().getName())
        .build();

    return new ResponseEntity<>(rnfDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handlerMethodArgumentNotValidException(MethodArgumentNotValidException manvException){
    List<FieldError> fieldErrors = manvException.getBindingResult().getFieldErrors();
    String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
    String fieldMessages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));

    ValidationErrorDetails rnfDetails =  ValidationErrorDetails.Builder
        .newBuilder()
        .timestamp(new Date().getTime())
        .status(HttpStatus.BAD_REQUEST.value())
        .title("Field Validation Error")
        .detail("Field Validation Error")
        .developerMessage(manvException.getClass().getName())
        .field(fields)
        .fieldMessage(fieldMessages)
        .build();

    return new ResponseEntity<>(rnfDetails, HttpStatus.BAD_REQUEST);
  }
}
