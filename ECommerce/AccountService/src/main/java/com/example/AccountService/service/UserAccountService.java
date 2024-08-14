package com.example.AccountService.service;


import com.example.AccountService.payload.UserAccountDto;
import com.example.AccountService.entity.UserAccount;
import com.example.AccountService.dao.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccountDto createAccount(UserAccountDto UserAccountDto) {
        UserAccount userAccount = UserAccountDto.toEntity(UserAccountDto);
        UserAccount savedAccount = userAccountRepository.save(userAccount);
        return UserAccountDto.fromEntity(savedAccount);
    }

    public Optional<UserAccountDto> getAccountById(Long id) {
        return userAccountRepository.findById(id).map(UserAccountDto::fromEntity);
    }

    public Optional<UserAccountDto> getAccountByEmail(String email) {
        return userAccountRepository.findByEmail(email).map(UserAccountDto::fromEntity);
    }

    public Optional<UserAccountDto> updateAccountPassword(Long id, String password) {
        Optional<UserAccount> existingAccountOpt = userAccountRepository.findById(id);

        if (existingAccountOpt.isPresent()) {
            UserAccount existingAccount = existingAccountOpt.get();
            if (password != null && !password.isEmpty()) {
                existingAccount.setPassword(password); // Update password if provided
            }
            UserAccount updatedAccount = userAccountRepository.save(existingAccount);
            return Optional.of(UserAccountDto.fromEntity(updatedAccount));
        }

        return Optional.empty();
    }

    public void deleteAccount(Long id) {
        userAccountRepository.deleteById(id);
    }
}

