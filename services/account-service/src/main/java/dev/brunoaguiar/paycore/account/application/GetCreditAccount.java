package dev.brunoaguiar.paycore.account.application;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCreditAccount {

    private final CreditAccountRepository creditAccountRepository;

    public GetCreditAccount(CreditAccountRepository creditAccountRepository) {
        this.creditAccountRepository = creditAccountRepository;
    }

    public CreditAccount execute(UUID accountId) {
        return creditAccountRepository.findById(accountId)
                .orElseThrow(() -> new CreditAccountNotFoundException(accountId));
    }
}
