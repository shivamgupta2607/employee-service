package com.shivam.employee.advice;

import com.shivam.employee.constants.AppConstants;
import com.shivam.employee.dto.ErrorResponse;
import com.shivam.employee.dto.ValidationError;
import com.shivam.employee.exception.BadRequestException;
import com.shivam.employee.exception.ForbiddenException;
import com.shivam.employee.exception.RecordNotFoundException;
import com.shivam.employee.exception.ServiceException;
import com.shivam.employee.exception.UnauthorizedException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.DataException;
import org.springframework.core.NestedRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This advice is used to send meaningful errors in the form of JSON back to client.
 */
@Log4j2
@RestControllerAdvice(basePackages = {AppConstants.BASE_PACKAGE})
public class ExceptionHandlerAdvice {

    /**
     * Handle service exception.
     *
     * @param exception service exception
     * @return Response Entity of error response
     */
    @ExceptionHandler({ServiceException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> handleApplicationException(
            ServiceException exception) {
        logErrors(exception);

        // Create error response object.
        ErrorResponse errorResponse = new ErrorResponse(exception);

        log.warn(errorResponse.toString());
        // Return error response with custom status.
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Handle validation exceptions.
     *
     * @param exception Validation exception.
     * @return Response Entity of error response.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException exception) {
        logErrors(exception);

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<ValidationError> validationErrors = new ArrayList<>();

        if (fieldErrors != null) {
            for (FieldError fieldError : fieldErrors) {
                ValidationError validationError = new ValidationError();
                validationError.setField(fieldError.getField());
                validationError.setError(fieldError.getDefaultMessage());
                validationErrors.add(validationError);
            }
        }

        ErrorResponse errorResponse = new ErrorResponse(AppConstants.FAILED_INVALID_METHOD_ARGUMENT,
                exception, HttpStatus.BAD_REQUEST, validationErrors);
        log.error(AppConstants.ERROR_RESPONSE, errorResponse.toString());

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Log errors.
     *
     * @param exception the exception
     */
    private void logErrors(Throwable exception) {
        log.error(AppConstants.EXCEPTION_THROWN, exception);
        log.error(exception.getLocalizedMessage());
    }

    /**
     * Handle illegal argument exception.
     *
     * @param exception the illegal argument exception
     * @return response entity of error response
     */
    @ExceptionHandler({IllegalArgumentException.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> handleIllegalArgumentException(
            IllegalArgumentException exception) {
        logErrors(exception);
        final List<ValidationError> validationErrors = new ArrayList<>();
        final ValidationError validationError = new ValidationError();
        final String localizedMessage = exception.getLocalizedMessage();
        validationError.setError(localizedMessage);
        validationErrors.add(validationError);
        final ErrorResponse errorResponse =
                new ErrorResponse(AppConstants.FAILED_INVALID_ARGUMENT + localizedMessage, exception,
                        HttpStatus.BAD_REQUEST, validationErrors);
        log.error(AppConstants.ERROR_RESPONSE, errorResponse.toString());

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Handle data integrity violation.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(value = {TransactionException.class, DataIntegrityViolationException.class,
            DataAccessException.class})
    protected ResponseEntity<Object> handleDataIntegrityViolation(
            final NestedRuntimeException exception) {
        logErrors(exception);
        ErrorResponse errorResponse = null;
        final Throwable cause = exception.getCause();
        if (cause == null) {
            errorResponse = new ErrorResponse(AppConstants.FAILED_DATABASE_INTEGRITY_VIOLATIONS, exception,
                    HttpStatus.CONFLICT);
        } else {
            if (cause instanceof ConstraintViolationException) {
                errorResponse = prepareErrorResponseForConstraintViolations(
                        (ConstraintViolationException) cause);

            } else if (cause instanceof DataException) {
                final SQLException sqlException = ((DataException) cause).getSQLException();
                errorResponse = new ErrorResponse(AppConstants.FAILED_DATABASE_VIOLATIONS, sqlException,
                        HttpStatus.CONFLICT);
            } else {
                final Throwable nestedCause = cause.getCause();
                /*
                 * Check nested exception
                 */
                if (nestedCause != null && nestedCause instanceof ConstraintViolationException) {
                    errorResponse = prepareErrorResponseForConstraintViolations(
                            (ConstraintViolationException) cause);
                } else {
                    errorResponse = new ErrorResponse(AppConstants.FAILED_DATABASE_INTEGRITY_VIOLATIONS,
                            exception, HttpStatus.CONFLICT);
                }
            }
        }

        log.error(AppConstants.ERROR_RESPONSE, errorResponse.toString());

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Prepare error response for constraint violations.
     *
     * @param cause the cause
     * @return the error response
     */
    private ErrorResponse prepareErrorResponseForConstraintViolations(
            final ConstraintViolationException cause) {
        final Set<ConstraintViolation<?>> constraintViolations = cause.getConstraintViolations();
        final List<ValidationError> validationErrors = new ArrayList<>();
        ValidationError validationError = null;
        String prop = null;
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            validationError = new ValidationError();
            prop = constraintViolation.getPropertyPath().toString();
            validationError.setField(prop);
            validationError.setError(String.format("%s %s but got: '%s'", prop,
                    constraintViolation.getMessage(), constraintViolation.getInvalidValue()));
            validationErrors.add(validationError);
        }

        return new ErrorResponse(AppConstants.FAILED_DATABASE_CONSTRAINT_VIOLATIONS, cause,
                HttpStatus.CONFLICT, validationErrors);
    }

    /**
     * Handle error conditions.
     *
     * @param exception the exception
     * @return Response Entity of error response
     */
    @ExceptionHandler({Exception.class})
    public @ResponseBody
    ResponseEntity<ErrorResponse> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        Throwable targetException = exception;
        final Throwable cause = exception.getCause();
        if (cause instanceof InvocationTargetException) {
            final InvocationTargetException invocationTargetException = (InvocationTargetException) cause;
            targetException = invocationTargetException.getTargetException();
        }

        final ErrorResponse errorResponse = new ErrorResponse(
                String.format(AppConstants.FAILED_EXCEPTION_PROCESSING_REQUEST,
                        targetException.getLocalizedMessage()),
                targetException, HttpStatus.INTERNAL_SERVER_ERROR);
        log.error(AppConstants.ERROR_RESPONSE, errorResponse.toString());

        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Handle bad request exception.
     *
     * @param exception, Object of BadRequestException
     * @return Response Entity of error response
     */
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<ErrorResponse> handleBadRequest(
            final BadRequestException exception) {
        logErrors(exception);

        // Create error response object.
        final ErrorResponse errorResponse = new ErrorResponse(exception);

        log.warn(errorResponse.toString());
        // Return error response with custom status.
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Handle bad request exception.
     *
     * @param exception, Object of UnauthorizedException
     * @return Response Entity of error response
     */
    @ExceptionHandler({UnauthorizedException.class})
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            final UnauthorizedException exception) {
        logErrors(exception);

        // Create error response object.
        final ErrorResponse errorResponse = new ErrorResponse(exception);

        log.warn(errorResponse.toString());
        // Return error response with custom status.
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Handle bad request exception.
     *
     * @param exception, Object of ForbiddenException
     * @return Response Entity of error response
     */
    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ErrorResponse> handleForbidden(
            final ForbiddenException exception) {
        logErrors(exception);

        // Create error response object.
        final ErrorResponse errorResponse = new ErrorResponse(exception);

        log.warn(errorResponse.toString());
        // Return error response with custom status.
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }

    /**
     * Handle bad request exception.
     *
     * @param exception, Object of RecordNotFoundException
     * @return Response Entity of error response
     */
    @ExceptionHandler({RecordNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleRecordNotFound(
            final RecordNotFoundException exception) {
        logErrors(exception);

        // Create error response object.
        final ErrorResponse errorResponse = new ErrorResponse(exception);

        log.warn(errorResponse.toString());
        // Return error response with custom status.
        return new ResponseEntity<>(errorResponse, errorResponse.getHttpStatus());
    }
}
