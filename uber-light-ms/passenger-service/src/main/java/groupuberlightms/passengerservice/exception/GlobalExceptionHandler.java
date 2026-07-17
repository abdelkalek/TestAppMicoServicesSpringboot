package groupuberlightms.passengerservice.exception;

import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Maps exceptions to RFC-7807 ProblemDetail responses.
 * Every error message is returned in BOTH English and Arabic under the
 * "message" property (and per-field under "errors" for validation).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Locale ARABIC = Locale.forLanguageTag("ar");

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /** Resolves a message key in English and Arabic. */
    private Map<String, String> bilingual(String code, Object... args) {
        return Map.of(
                "en", messageSource.getMessage(code, args, Locale.ENGLISH),
                "ar", messageSource.getMessage(code, args, ARABIC));
    }

    @ExceptionHandler(PassengerNotFoundException.class)
    public ProblemDetail handleNotFound(PassengerNotFoundException ex) {
        Map<String, String> message = bilingual("error.passenger.notfound", ex.getId());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, message.get("en"));
        problem.setTitle("Passenger not found");
        problem.setProperty("message", message);
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Map<String, String>> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            // FieldError is a MessageSourceResolvable: its codes (e.g.
            // "NotBlank.passengerRequest.email", ..., "NotBlank") are looked
            // up in the bundles, falling back to the default message.
            errors.put(fieldError.getField(), Map.of(
                    "en", messageSource.getMessage(fieldError, Locale.ENGLISH),
                    "ar", messageSource.getMessage(fieldError, ARABIC)));
        }
        Map<String, String> message = bilingual("error.validation");
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, message.get("en"));
        problem.setTitle("Invalid request");
        problem.setProperty("message", message);
        problem.setProperty("errors", errors);
        return problem;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleConflict(DataIntegrityViolationException ex) {
        Map<String, String> message = bilingual("error.conflict.email");
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, message.get("en"));
        problem.setTitle("Conflict");
        problem.setProperty("message", message);
        return problem;
    }
}
