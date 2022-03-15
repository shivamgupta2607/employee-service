package com.shivam.employee.exception;

/**
 * Exception thrown in case an request is made without token.
 *
 * @Date: 10/03/20
 * @Time: 10.26
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RequestWithoutTokenException extends ServiceException {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -418514577885795898L;

    /**
     * The Constant HTTP_STATUS.
     */
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;

    /**
     * Instantiates a new request without token exception.
     *
     * @param errorMessage the error message
     */
    public RequestWithoutTokenException(final String errorMessage) {
        super(errorMessage, HTTP_STATUS);
    }

    /**
     * Instantiates a new request without token exception.
     *
     * @param throwable the throwable
     */
    public RequestWithoutTokenException(final Throwable throwable) {
        super(throwable, HTTP_STATUS);
    }
}
