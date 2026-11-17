package dev.brunoaguiar.paycore.account.adapter.in.web;

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
}
