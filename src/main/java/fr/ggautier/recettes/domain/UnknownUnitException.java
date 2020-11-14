package fr.ggautier.recettes.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnknownUnitException extends Exception {

    private static final String MESSAGE = "Unknown unit : %s";

    UnknownUnitException(final String unit) {
        super(String.format(MESSAGE, unit));
    }
}
