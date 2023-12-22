package com.bootcamp.controller;

import com.bootcamp.service.IBankAccountsService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.cloudinary.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequestMapping("/bankAccounts")
@RequiredArgsConstructor
public class BankAccounts {

    private final IBankAccountsService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<com.bootcamp.model.BankAccounts>>> findAll() {
        Flux<com.bootcamp.model.BankAccounts> fx = service.findAll(); //Flux<Client>

        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(fx)
        ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<com.bootcamp.model.BankAccounts>> findBy(@PathVariable("id") String id) {
        return service.findById(id)
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<com.bootcamp.model.BankAccounts>> save(@RequestBody com.bootcamp.model.BankAccounts bankAccounts, final ServerHttpRequest req) {
        return service.save(bankAccounts)
                .map(e -> ResponseEntity
                        .created(URI.create(req.getURI().toString().concat("/").concat(e.getAccountId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<com.bootcamp.model.BankAccounts>> update(@PathVariable("id") String id, @RequestBody com.bootcamp.model.BankAccounts bankAccounts) {
        bankAccounts.setAccountId(id);

        Mono<com.bootcamp.model.BankAccounts> monoBody = Mono.just(bankAccounts);
        Mono<com.bootcamp.model.BankAccounts> monoDB = service.findById(id);

        /*service.findById(id).hasElement()
                .map(status -> {
                    if(status){
                        service.update(bankAccounts)
                    }
                })*/

        return monoDB.zipWith(monoBody, (db, c) -> {
                    db.setAccountId(id);
                    db.setType(c.getType());
                    db.setMaintenanceFee(c.getMaintenanceFee());
                    db.setMonthlyMovementLimit(c.getMonthlyMovementLimit());
                    db.setSpecificMovementDay(c.getSpecificMovementDay());
                    db.setClientId(c.getClientId());
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
                .flatMap(e -> service.delete(e.getClientId())
                        //.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                        .thenReturn(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    ///////////////////////////////////
    @PostMapping("/v1/upload/{id}")
    public Mono<ResponseEntity<com.bootcamp.model.BankAccounts>> uploadV1(@PathVariable("id") String id, @RequestPart("file") FilePart file) throws Exception{
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "xxxx",
                "api_key", "xxx",
                "api_secret", "xxxxx"
        ));

        File f = Files.createTempFile("temp", file.filename()).toFile();

        return file.transferTo(f)
                .then(service.findById(id)
                        .flatMap(c -> {
                            Map response;

                            try {
                                response = cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto"));
                                JSONObject json = new JSONObject(response);
                                String url = json.getString("url");

                                c.setType(url);

                                return service.update(c).thenReturn(ResponseEntity.ok().body(c));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                ).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/v2/upload/{id}")
    public Mono<ResponseEntity<com.bootcamp.model.BankAccounts>> uploadV2(@PathVariable("id") String id, @RequestPart("file") FilePart file) throws Exception {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "xxxx",
                "api_key", "xxxx",
                "api_secret", "xxxx"
        ));

        return service.findById(id)
                .flatMap(c -> {

                    try{
                        File f = Files.createTempFile("temp", file.filename()).toFile();
                        file.transferTo(f).block();

                        Map response = cloudinary.uploader().upload(f, ObjectUtils.asMap("resource_type", "auto"));
                        JSONObject json = new JSONObject(response);
                        String url = json.getString("url");

                        c.setType(url);

                        return service.update(c).thenReturn(ResponseEntity.ok().body(c));
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }
                })
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }
}
