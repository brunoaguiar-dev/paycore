package dev.brunoaguiar.paycore.account.adapter.in.web;

import dev.brunoaguiar.paycore.account.application.GetCreditAccount;
import dev.brunoaguiar.paycore.account.application.OpenCreditAccount;
import dev.brunoaguiar.paycore.account.application.ReserveCredit;
import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.Money;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
class CreditAccountController {

    private final OpenCreditAccount openCreditAccount;
    private final GetCreditAccount getCreditAccount;
    private final ReserveCredit reserveCredit;

    CreditAccountController(OpenCreditAccount openCreditAccount, GetCreditAccount getCreditAccount, ReserveCredit reserveCredit) {
        this.openCreditAccount = openCreditAccount;
        this.getCreditAccount = getCreditAccount;
        this.reserveCredit = reserveCredit;
    }

    @PostMapping
    ResponseEntity<CreditAccountResponse> open(@Valid @RequestBody OpenCreditAccountRequest request) {
        CreditAccount account = openCreditAccount.execute(request.cardId(), new Money(request.creditLimit()));

        return ResponseEntity
                .created(URI.create("/accounts/" + account.id()))
                .body(CreditAccountResponse.from(account));
    }

    @GetMapping("/{id}")
    CreditAccountResponse getById(@PathVariable UUID id) {
        return CreditAccountResponse.from(getCreditAccount.execute(id));
    }

    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void reserve(@Valid @RequestBody ReserveCreditRequest request) {
        reserveCredit.execute(request.cardId(), new Money(request.amount()));
    }
}
