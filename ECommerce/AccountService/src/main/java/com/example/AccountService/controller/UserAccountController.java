package com.example.AccountService.controller;

import com.example.AccountService.payload.UserAccountDto;
import com.example.AccountService.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("/create")
    public ResponseEntity<UserAccountDto> createAccount(@RequestBody UserAccountDto UserAccountDto, @RequestParam String password) {
        UserAccountDto createdAccount = userAccountService.createAccount(UserAccountDto);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAccountDto> getAccountById(@PathVariable Long id) {
        Optional<UserAccountDto> accountOpt = userAccountService.getAccountById(id);
        return accountOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/email")
    public ResponseEntity<UserAccountDto> getAccountByEmail(@RequestParam String email) {
        Optional<UserAccountDto> accountOpt = userAccountService.getAccountByEmail(email);
        return accountOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserAccountDto> updateAccount(@PathVariable Long id, @RequestBody UserAccountDto userAccountDTO, @RequestParam(required = false) String password) {
        Optional<UserAccountDto> updatedAccountOpt = userAccountService.updateAccount(id, userAccountDTO, password);
        return updatedAccountOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        userAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}

