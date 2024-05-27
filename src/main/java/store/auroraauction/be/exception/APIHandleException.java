package store.auroraauction.be.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class APIHandleException {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Object> handleInvalidUsernamePasword(BadCredentialsException ex) {
        return new ResponseEntity<>("phone or password not corrected", HttpStatus.FORBIDDEN);// loi phan quyen
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleDuplicatePhone(SQLIntegrityConstraintViolationException ex) {
        return new ResponseEntity<>("Duplicated phone !!!", HttpStatus.BAD_REQUEST);// loi request ko phu hop
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object> handleAuthException(AuthException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);// loi phan quyen
    }
}
