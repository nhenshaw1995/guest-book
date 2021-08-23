package com.bt.tech.test.GuestBook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "entry not found")
public class EntryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -1466086691624298719L;
}
