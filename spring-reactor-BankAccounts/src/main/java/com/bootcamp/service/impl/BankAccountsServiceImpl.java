package com.bootcamp.service.impl;

import com.bootcamp.model.BankAccounts;
import com.bootcamp.repo.IBankAccountsRepo;
import com.bootcamp.repo.IGenericRepo;
import com.bootcamp.service.IBankAccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BankAccountsServiceImpl extends CRUDImpl<BankAccounts, String> implements IBankAccountsService {

    private final IBankAccountsRepo repo;

    @Override
    protected IGenericRepo<BankAccounts, String> getRepo() {
        return repo;
    }
}