package org.sixtead.techrequests.exceptions;

public class UniqueConstraintException extends ServiceException {

    public UniqueConstraintException() {
    }

    public UniqueConstraintException(String message) {
        super(message);
    }
}
