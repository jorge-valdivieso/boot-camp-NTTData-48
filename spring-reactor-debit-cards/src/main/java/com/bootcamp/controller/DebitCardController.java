package com.bootcamp.controller;

import com.bootcamp.model.DebitCard;
import com.bootcamp.service.IDebitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.net.URI;


@RestController
@RequestMapping("/debitCard")
@RequiredArgsConstructor
public class DebitCardController {
    private final IDebitCardService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<DebitCard>>> findAll() {
        Flux<DebitCard> fx = service.findAll(); //Flux<DebitCard>

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<DebitCard>> findBy(@PathVariable("id") String id) {
        return service.findById(id)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<DebitCard>> save(@RequestBody DebitCard debitCard, final ServerHttpRequest req) {
        return service.save(debitCard)
                .map(e -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(e.getDebitCardId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<DebitCard>> update(@PathVariable("id") String id, @RequestBody DebitCard debitCard) {
        debitCard.setDebitCardId(id);

        Mono<DebitCard> monoBody = Mono.just(debitCard);
        Mono<DebitCard> monoDB = service.findById(id);

        /*service.findById(id).hasElement()
                .map(status -> {
                    if(status){
                        service.update(DebitCard)
                    }
                })*/

        return monoDB.zipWith(monoBody, (db, c) -> {
                    db.setDebitCardId(id);
                    db.setClientId(c.getClientId());
                    db.setPrincipalAccountId(c.getPrincipalAccountId());
                    db.setIssueDate(c.getIssueDate());
                    db.setExpiryDate(c.getExpiryDate());
                    db.setStatus(c.getStatus());
                    db.setCurrentBalance(c.getCurrentBalance());
                    return db;
                })
                .flatMap(service::update) //operaciones de DB 99% flatMap
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id) {
        return service.findById(id)
                .flatMap(e -> service.delete(e.getDebitCardId())
                        //.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                        .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
