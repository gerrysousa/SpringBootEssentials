package br.com.springbootessentials.handler;

import br.com.springbootessentials.error.ResourceNotFoundDetails;
import br.com.springbootessentials.error.ResourceNotFoundException;
import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
