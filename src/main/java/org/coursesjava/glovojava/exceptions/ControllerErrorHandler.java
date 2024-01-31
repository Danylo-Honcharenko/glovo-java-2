package org.coursesjava.glovojava.exceptions;

import org.coursesjava.glovojava.utilit.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class ControllerErrorHandler {
    @ExceptionHandler(value = OrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleOrderNotFoundException() {
        return ResponseHandler.response("Order not found!", HttpStatus.NOT_FOUND);
    }
}
