package com.bootcamp.controller;

import com.bootcamp.model.MobileWalletUsers;
import com.bootcamp.service.IMobileWalletUsersService;
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
@RequestMapping("/mobile-Wallet-Users")
@RequiredArgsConstructor
public class MobileWalletUsersController {
    private final IMobileWalletUsersService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<MobileWalletUsers>>> findAll() {
        Flux<MobileWalletUsers> fx = service.findAll();

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<MobileWalletUsers>> findBy(@PathVariable("id") String id) {
        return service.findById(id)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<MobileWalletUsers>> save(@RequestBody MobileWalletUsers mobileWalletUsers, final ServerHttpRequest req) {
        return service.save(mobileWalletUsers)
                .map(e -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(e.getYankUserId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<MobileWalletUsers>> update(@PathVariable("id") String id, @RequestBody MobileWalletUsers mobileWalletUsers) {
        mobileWalletUsers.setYankUserId(id);

        Mono<MobileWalletUsers> monoBody = Mono.just(mobileWalletUsers);
        Mono<MobileWalletUsers> monoDB = service.findById(id);

        return monoDB.zipWith(monoBody, (db, c) -> {
                    db.setYankUserId(id);
                    db.setIdentificationDocument(c.getIdentificationDocument());
                    db.setMobileNumber(c.getMobileNumber());
                    db.setImei(c.getImei());
                    db.setEmailAddress(c.getEmailAddress());
                    db.setStatus(c.getStatus());
                    db.setCreationDate(c.getCreationDate());
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
                .flatMap(e -> service.delete(e.getYankUserId())
                        //.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                        .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
