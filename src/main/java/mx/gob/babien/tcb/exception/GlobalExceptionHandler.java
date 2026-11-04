package mx.gob.babien.tcb.exception;

import mx.gob.babien.tcb.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponseDto> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(error -> error.getDefaultMessage() == null ? "Solicitud inválida." : error.getDefaultMessage())
                .orElse("Solicitud inválida.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponseDto(message));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiErrorResponseDto> handleBadRequest(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponseDto(exception.getMessage()));
    }

    @ExceptionHandler(ServiceIntegrationException.class)
    public ResponseEntity<ApiErrorResponseDto> handleIntegration(ServiceIntegrationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiErrorResponseDto(exception.getMessage()));
    }
}
