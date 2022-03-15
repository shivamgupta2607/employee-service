package com.shivam.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The Entity Already In Use Exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordAlreadyInUseException extends ServiceException {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -1082732770469410017L;

    /**
     * The Constant HTTP_STATUS.
     */
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_ACCEPTABLE;

    /**
     * Instantiates a new entity already in use exception.
     *
     * @param errorMessage the error message
     */
    public RecordAlreadyInUseException(final String errorMessage) {
        super(errorMessage, HTTP_STATUS);
    }

    /**
     * Instantiates a new entity already in use exception.
     *
     * @param throwable the throwable
     */
    public RecordAlreadyInUseException(final Throwable throwable) {
        super(throwable, HTTP_STATUS);
    }
}