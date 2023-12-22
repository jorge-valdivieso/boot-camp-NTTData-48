package com.bootcamp.service.impl;

import com.bootcamp.model.BankTransfers;
import com.bootcamp.repo.IGenericRepo;
import com.bootcamp.repo.IBankTransfersRepo;
import com.bootcamp.service.IBankTransfersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankTransfersServiceImpl  extends CRUDImpl<BankTransfers, String> implements IBankTransfersService {

    private final IBankTransfersRepo repo;

    @Override
    protected IGenericRepo<BankTransfers, String> getRepo() {
        return repo;
    }
}