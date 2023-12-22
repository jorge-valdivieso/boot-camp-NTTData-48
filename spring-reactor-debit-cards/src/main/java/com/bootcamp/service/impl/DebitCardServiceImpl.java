package com.bootcamp.service.impl;

import com.bootcamp.model.DebitCard;
import com.bootcamp.repo.IDebitCardRepo;
import com.bootcamp.repo.IGenericRepo;
import com.bootcamp.service.IDebitCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DebitCardServiceImpl extends CRUDImpl<DebitCard, String> implements IDebitCardService {
    private final IDebitCardRepo repo;
    @Override
    protected IGenericRepo<DebitCard, String> getRepo() {
        return repo;
    }
}
