package pl.Tiguarces.TGbook.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.Tiguarces.TGbook.security.exceptions.EntityRecordExists;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class TGbExceptionHandler {

    @ExceptionHandler({ RuntimeException.class, EntityRecordExists.class })
    public ResponseEntity<?> handleRuntimeException(final RuntimeException exception) {
        return ResponseEntity
                .status(409)
                .body(new Response(exception.getMessage()));
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exception) {
        return ResponseEntity
                .status(409)
                .body(getErrors(exception));
    }

    private List<String> getErrors(final MethodArgumentNotValidException exception) {
        final BindingResult result = exception.getBindingResult();
        final List<String> errors = new ArrayList<>(0);

        for(final FieldError error: result.getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        } return errors;
    }

    record Response(String message) {}
}
