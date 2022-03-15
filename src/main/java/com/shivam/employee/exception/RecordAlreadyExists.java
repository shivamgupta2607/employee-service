package com.shivam.employee.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class RecordAlreadyExists extends ServiceException {
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -1082732770469410017L;

    /**
     * The Constant HTTP_STATUS.
     */
    private static final HttpStatus HTTP_STATUS = HttpStatus.CONFLICT;

    /**
     * Instantiates a new entity already exists exception.
     *
     * @param errorMessage the error message
     */
    public RecordAlreadyExists(final String errorMessage) {
        super(errorMessage, HTTP_STATUS);
    }

    /**
     * Instantiates a new entity already exists exception.
     *
     * @param throwable the throwable
     */
    public RecordAlreadyExists(final Throwable throwable) {
        super(throwable, HTTP_STATUS);
    }
}
