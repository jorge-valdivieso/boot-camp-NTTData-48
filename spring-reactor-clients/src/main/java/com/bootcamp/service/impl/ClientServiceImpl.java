package com.bootcamp.service.impl;

import com.bootcamp.model.Client;
import com.bootcamp.repo.IClientRepo;
import com.bootcamp.repo.IGenericRepo;
import com.bootcamp.service.IClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl extends CRUDImpl<Client, String> implements IClientService {

    private final IClientRepo repo;


    @Override
    protected IGenericRepo<Client, String> getRepo() {
        return repo;
    }
}
