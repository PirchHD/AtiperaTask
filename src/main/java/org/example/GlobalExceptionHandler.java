package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Globalny handler wyjątków
 */
@ControllerAdvice
public class GlobalExceptionHandler
{

    /**
     * Obsługuje wyjątek UserNotFoundException, który jest rzucany,
     * gdy użytkownik GitHub nie zostanie znaleziony.
     *
     * @param ex wyjątek UserNotFoundException
     *
     * @return odpowiedź 404 z informacją o błędzie w formacie JSON
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("status", ex.getStatus());
        body.put("message", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

    /**
     * Obsługuje wszystkie inne (nieprzechwycone) wyjątki.
     * Zwraca status 500 i komunikat o błędzie.
     *
     * @param ex dowolny wyjątek
     *
     * @return odpowiedź 500 z komunikatem błędu
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex)
    {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 500);
        body.put("message", "Unexpected error: " + ex.getMessage());
        return ResponseEntity.status(500).body(body);
    }
}
