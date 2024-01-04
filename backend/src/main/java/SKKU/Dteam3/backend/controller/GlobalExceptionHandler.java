package SKKU.Dteam3.backend.controller;

import SKKU.Dteam3.backend.error.ErrorCode;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.security.SignatureException;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorCode handleIllegalArgumentException(IllegalArgumentException e) {
        return new ErrorCode(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(SignatureException.class)
    public ErrorCode handleSignatureException(SignatureException e) {
        return new ErrorCode(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MalformedJwtException.class)
    public ErrorCode handleMalformedJwtException(MalformedJwtException e) {
        return new ErrorCode(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorCode handleExpiredJwtException(ExpiredJwtException e) {
        return new ErrorCode(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnsupportedJwtException.class)
    public ErrorCode handleUnsupportedJwtException(UnsupportedJwtException e) {
        return new ErrorCode(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
}
