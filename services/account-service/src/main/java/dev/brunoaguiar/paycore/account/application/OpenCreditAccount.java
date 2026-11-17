package dev.brunoaguiar.paycore.account.application;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccountRepository;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OpenCreditAccount {

    private final CreditAccountRepository creditAccountRepository;

    public OpenCreditAccount(CreditAccountRepository creditAccountRepository) {
        this.creditAccountRepository = creditAccountRepository;
    }

    public CreditAccount execute(UUID cardId, Money creditLimit) {
        CreditAccount account = new CreditAccount(cardId, creditLimit);

        if (creditAccountRepository.existsByCardId(cardId)) {
            throw new CreditAccountAlreadyExistsException(cardId);
        }
        creditAccountRepository.save(account);
        return account;
    }
}
