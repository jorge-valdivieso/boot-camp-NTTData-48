package com.bootcamp.service.impl;

import com.bootcamp.model.MobileWalletUsers;
import com.bootcamp.repo.IGenericRepo;
import com.bootcamp.repo.IMobileWalletUsersRepo;
import com.bootcamp.service.IMobileWalletUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MobileWalletUsersImpl extends CRUDImpl<MobileWalletUsers, String> implements IMobileWalletUsersService {
    private final IMobileWalletUsersRepo repo;
    @Override
    protected IGenericRepo<MobileWalletUsers, String> getRepo() {
        return repo;
    }
}
