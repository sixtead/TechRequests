package org.sixtead.techrequests.exceptions;

import java.util.function.Supplier;

public class NotFoundException extends ServiceException {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

}
