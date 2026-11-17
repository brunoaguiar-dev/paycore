package dev.brunoaguiar.paycore.account.adapter.in.web;

import dev.brunoaguiar.paycore.account.application.CreditAccountAlreadyExistsException;
import dev.brunoaguiar.paycore.account.application.CreditAccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(CreditAccountNotFoundException.class)
    ProblemDetail handleCreditAccountNotFound(CreditAccountNotFoundException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        problem.setTitle("Conta de crédito não encontrada");
        return problem;
    }

    @ExceptionHandler(CreditAccountAlreadyExistsException.class)
    ProblemDetail handleCreditAccountAlreadyExists(CreditAccountAlreadyExistsException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());
        problem.setTitle("Conta de crédito já existe");
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ProblemDetail handleInvalidRequest(MethodArgumentNotValidException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "A requisição tem campos inválidos");
        problem.setTitle("Dados inválidos");

        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        problem.setProperty("errors", errors);

        return problem;
    }
}
