package fr.ggautier.recettes.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
class NotMatchingIdentifiersException extends Exception {

    public static final String MESSAGE = "Identifiers provided in the path and in the body do not match";

    NotMatchingIdentifiersException() {
        super(MESSAGE);
    }
}
