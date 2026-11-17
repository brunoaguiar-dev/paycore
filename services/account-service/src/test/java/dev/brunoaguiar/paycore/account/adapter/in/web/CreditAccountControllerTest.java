package dev.brunoaguiar.paycore.account.adapter.in.web;

import dev.brunoaguiar.paycore.account.application.CreditAccountAlreadyExistsException;
import dev.brunoaguiar.paycore.account.application.CreditAccountNotFoundException;
import dev.brunoaguiar.paycore.account.application.GetCreditAccount;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import dev.brunoaguiar.paycore.account.application.OpenCreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CreditAccountController.class)
class CreditAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OpenCreditAccount openCreditAccount;

    @MockitoBean
    private GetCreditAccount getCreditAccount;

    @Test
    void opensAccount() throws Exception {
        UUID cardId = UUID.randomUUID();
        CreditAccount account = new CreditAccount(cardId, Money.of("5000.00"));
        when(openCreditAccount.execute(cardId, Money.of("5000.00"))).thenReturn(account);

        mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        { "cardId": "%s", "creditLimit": 5000.00 }
                        """.formatted(cardId)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/accounts/" + account.id()))
                .andExpect(jsonPath("$.id").value(account.id().toString()))
                .andExpect(jsonPath("$.cardId").value(cardId.toString()))
                .andExpect(jsonPath("$.creditLimit").value(5000.00))
                .andExpect(jsonPath("$.availableCredit").value(5000.00));
    }

    @Test
    void returnsAccount() throws Exception {
        CreditAccount account = CreditAccount.restore(
                UUID.randomUUID(), UUID.randomUUID(), Money.of("5000.00"), Money.of("1200.50"));
        when(getCreditAccount.execute(account.id())).thenReturn(account);

        mockMvc.perform(get("/accounts/{id}", account.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(account.id().toString()))
                .andExpect(jsonPath("$.creditLimit").value(5000.00))
                .andExpect(jsonPath("$.availableCredit").value(1200.50));
    }

    @Test
    void returnsNotFoundWhenAccountDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(getCreditAccount.execute(id)).thenThrow(new CreditAccountNotFoundException(id));

        mockMvc.perform(get("/accounts/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Conta de crédito não encontrada"))
                .andExpect(jsonPath("$.detail").value("Conta de crédito não encontrada: " + id));
    }

    @Test
    void returnsConflictWhenCardAlreadyHasAccount() throws Exception {
        UUID cardId = UUID.randomUUID();
        when(openCreditAccount.execute(cardId, Money.of("5000.00")))
                .thenThrow(new CreditAccountAlreadyExistsException(cardId));

        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "cardId": "%s", "creditLimit": 5000.00 }
                                """.formatted(cardId)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Conta de crédito já existe"));
    }

    @Test
    void returnsBadRequestWhenCreditLimitIsZero() throws Exception {
        mockMvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "cardId": "%s", "creditLimit": 0 }
                                """.formatted(UUID.randomUUID())))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Dados inválidos"))
                .andExpect(jsonPath("$.errors.creditLimit").value("O limite de crédito deve ser maior que zero"));
    }
}
