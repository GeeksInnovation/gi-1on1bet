package com._on1bet.authservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MissingRequestValueException;

import com._on1betutils.utils1on1bet._on1BetErrorResponse;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class _on1BetException extends RuntimeException {

    @ExceptionHandler(MissingRequestValueException.class)
    public Mono<ResponseEntity<_on1BetErrorResponse>> handleValidationException(MissingRequestValueException ex) {
        _on1BetErrorResponse errorResponse = new _on1BetErrorResponse("1001", "Mismatch in input");
        return Mono.just(ResponseEntity.badRequest().body(errorResponse));
    }

}
