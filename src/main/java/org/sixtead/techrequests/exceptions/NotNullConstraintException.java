package org.sixtead.techrequests.exceptions;

public class NotNullConstraintException extends ServiceException {

    public NotNullConstraintException() {
    }

    public NotNullConstraintException(String message) {
        super(message);
    }
}
