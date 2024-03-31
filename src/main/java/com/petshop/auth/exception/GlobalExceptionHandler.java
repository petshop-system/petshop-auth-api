package com.petshop.auth.exception;


//import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.util.WebUtils;

@RestControllerAdvice
//@Tag(name = "GlobalExceptionHandler", description = "Global Exception Handler: management APIs")
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private HttpHeaders defaultHttpHeaders () {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return headers;
    }

    /** Provides handling for exceptions throughout this service. */
    @ExceptionHandler({NotFoundException.class})
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public final ResponseEntity<ApiError> handleNotFoundException(Exception ex, WebRequest request) {
        NotFoundException notF = (NotFoundException) ex;
        return this.handleExceptionInternal(ex,
                new ApiError(notF.getMessage()),
                this.defaultHttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({InternalServerErrorException.class})
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final ResponseEntity<ApiError> handleInternalServerErrorException(Exception ex, WebRequest request) {
        InternalServerErrorException isre = (InternalServerErrorException) ex;
        return this.handleExceptionInternal(ex, new ApiError(isre.getMessage()),
                this.defaultHttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler({UnauthorizedException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<ApiError> handleUnauthorizedException(Exception ex, WebRequest request) {
        UnauthorizedException ue = (UnauthorizedException) ex;
        return this.handleExceptionInternal(ue, new ApiError(ue.getMessage()),
                this.defaultHttpHeaders(),
                HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({ValidationException.class})
    public final ResponseEntity<ApiError> handleValidationException(Exception ex, WebRequest request) {
        ValidationException val = (ValidationException) ex;
        return this.handleExceptionInternal(ex, new ApiError(val.getMessages()),
                this.defaultHttpHeaders(),
                val.statusCode, request);
    }

    @ExceptionHandler({ForbiddenException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public final ResponseEntity<ApiError> handleForbiddenException(Exception ex, WebRequest request) {
        ForbiddenException ue = (ForbiddenException) ex;
        return this.handleExceptionInternal(ue, new ApiError(ue.getMessage()),
                this.defaultHttpHeaders(),
                HttpStatus.FORBIDDEN, request);
    }

    /** A single place to customize the response body of all Exception types. */
    protected ResponseEntity<ApiError> handleExceptionInternal(Exception ex, ApiError body,
                                                               HttpHeaders headers, HttpStatus status,
                                                               WebRequest request) {
        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, ex, WebRequest.SCOPE_REQUEST);
            return null;
        }

        logger.atError()
                .setCause(ex)
                .setMessage(body.getMessage())
                .addKeyValue("requestID", request.getHeader("request_id"))
                .addKeyValue("status_code", status.name())
                .log();

        return ResponseEntity.status(status).body(body);
    }
}
