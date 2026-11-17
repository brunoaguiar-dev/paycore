package dev.brunoaguiar.paycore.account.adapter.in.web;

import dev.brunoaguiar.paycore.account.application.OpenCreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.Money;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/accounts")
class CreditAccountController {

    private final OpenCreditAccount openCreditAccount;

    CreditAccountController(OpenCreditAccount openCreditAccount) {
        this.openCreditAccount = openCreditAccount;
    }

    @PostMapping
    ResponseEntity<CreditAccountResponse> open(@Valid @RequestBody OpenCreditAccountRequest request) {
        CreditAccount account = openCreditAccount.execute(request.cardId(), new Money(request.creditLimit()));

        return ResponseEntity
                .created(URI.create("/accounts/" + account.id()))
                .body(CreditAccountResponse.from(account));
    }
}
