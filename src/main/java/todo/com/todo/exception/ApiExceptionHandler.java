package todo.com.todo.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(TarefaNaoEncontradaException.class)
    public ResponseEntity<ErroResponse> tratarNaoEncontrada(TarefaNaoEncontradaException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErroResponse(exception.getMessage()));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErroResponse> tratarValidacao(Exception exception) {
        String mensagem;
        if (exception instanceof MethodArgumentNotValidException methodException) {
            mensagem = methodException.getBindingResult().getFieldErrors().stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.joining(", "));
        } else {
            mensagem = exception.getMessage();
        }
        return ResponseEntity.badRequest().body(new ErroResponse(mensagem));
    }

    public record ErroResponse(String mensagem, LocalDateTime timestamp) {
        public ErroResponse(String mensagem) {
            this(mensagem, LocalDateTime.now());
        }
    }
}
