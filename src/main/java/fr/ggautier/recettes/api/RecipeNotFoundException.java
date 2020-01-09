package fr.ggautier.recettes.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when an operation is requested on a recipe and that recipe could not be retrieved.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND)
class RecipeNotFoundException extends Exception {

}
