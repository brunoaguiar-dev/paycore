package dev.brunoaguiar.paycore.card.adapter.in.web;

import dev.brunoaguiar.paycore.card.application.BlockCard;
import dev.brunoaguiar.paycore.card.application.GetCard;
import dev.brunoaguiar.paycore.card.application.IssueCard;
import dev.brunoaguiar.paycore.card.application.UnblockCard;
import dev.brunoaguiar.paycore.card.domain.Card;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
class CardController {

    private final IssueCard issueCard;
    private final GetCard getCard;
    private final BlockCard blockCard;
    private final UnblockCard unblockCard;

    CardController(IssueCard issueCard, GetCard getCard, BlockCard blockCard, UnblockCard unblockCard) {
        this.issueCard = issueCard;
        this.getCard = getCard;
        this.blockCard = blockCard;
        this.unblockCard = unblockCard;
    }

    @PostMapping
    ResponseEntity<CardResponse> issue(@Valid @RequestBody IssueCardRequest request) {
        Card card = issueCard.execute(request.holderName());

        return ResponseEntity
                .created(URI.create("/cards/" + card.id()))
                .body(CardResponse.from(card));
    }

    @GetMapping("/{id}")
    CardResponse getById(@PathVariable UUID id) {
        return CardResponse.from(getCard.execute(id));
    }

    @PostMapping("/{id}/block")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void block(@PathVariable UUID id) {
        blockCard.execute(id);
    }

    @PostMapping("/{id}/unblock")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void unblock(@PathVariable UUID id) {
        unblockCard.execute(id);
    }
}
