package dev.brunoaguiar.paycore.account.application;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccountRepository;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ReserveCredit {

    private final CreditAccountRepository creditAccountRepository;

    public ReserveCredit(CreditAccountRepository creditAccountRepository) {
        this.creditAccountRepository = creditAccountRepository;
    }

    @Transactional
    public void execute(UUID cardId, Money amount) {
        CreditAccount account = creditAccountRepository.findByCardId(cardId)
                .orElseThrow(() -> CreditAccountNotFoundException.forCard(cardId));
        account.reserve(amount);
        creditAccountRepository.save(account);
    }
}
