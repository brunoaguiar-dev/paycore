package dev.brunoaguiar.paycore.card.adapter.in.web;

import dev.brunoaguiar.paycore.card.application.*;
import dev.brunoaguiar.paycore.card.domain.CanceledCardException;
import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardNumber;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CardController.class)
class CardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IssueCard issueCard;

    @MockitoBean
    private GetCard getCard;

    @MockitoBean
    private BlockCard blockCard;

    @MockitoBean
    private UnblockCard unblockCard;

    @Test
    void issuesCard() throws Exception {
        Card card = new Card(new CardNumber("9999990000001234"), "MARIA SILVA", YearMonth.of(2031, 11));
        when(issueCard.execute("MARIA SILVA")).thenReturn(card);

        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "holderName": "MARIA SILVA" }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/cards/" + card.id()))
                .andExpect(jsonPath("$.id").value(card.id().toString()))
                .andExpect(jsonPath("$.number").value("************1234"))
                .andExpect(jsonPath("$.holderName").value("MARIA SILVA"))
                .andExpect(jsonPath("$.expiration").value("2031-11"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void returnsCard() throws Exception {
        Card card = new Card(new CardNumber("9999990000001234"), "MARIA SILVA", YearMonth.of(2031, 11));
        when(getCard.execute(card.id())).thenReturn(card);

        mockMvc.perform(get("/cards/{id}", card.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(card.id().toString()))
                .andExpect(jsonPath("$.number").value("************1234"))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void blocksCard() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/cards/{id}/block", id))
                .andExpect(status().isNoContent());

        verify(blockCard).execute(id);
    }

    @Test
    void unblocksCard() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(post("/cards/{id}/unblock", id))
                .andExpect(status().isNoContent());

        verify(unblockCard).execute(id);
    }

    @Test
    void returnsNotFoundWhenCardDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(getCard.execute(id)).thenThrow(new CardNotFoundException(id));

        mockMvc.perform(get("/cards/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Cartão não encontrado"))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.detail").value("Cartão não encontrado: " + id));
    }

    @Test
    void returnsConflictWhenCardIsCanceled() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(new CanceledCardException("Cartão cancelado não pode ser bloqueado"))
                .when(blockCard).execute(id);

        mockMvc.perform(post("/cards/{id}/block", id))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title").value("Cartão cancelado"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("Cartão cancelado não pode ser bloqueado"));
    }

    @Test
    void returnsBadRequestWhenHolderNameIsBlank() throws Exception {
        mockMvc.perform(post("/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                { "holderName": " " }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Dados inválidos"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.errors.holderName").value("O nome do titular é obrigatório"));
    }
}
