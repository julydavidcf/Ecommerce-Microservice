package com.example.AccountService.controller;

import com.example.AccountService.payload.UserAccountDto;
import com.example.AccountService.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("")
    public ResponseEntity<UserAccountDto> createAccount(@RequestBody UserAccountDto UserAccountDto) {
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

    @PutMapping("/{id}")
    public ResponseEntity<UserAccountDto> updateAccount(@PathVariable Long id, @RequestBody Map<String, String> requestBody) {
        // Extract the password from the request body
        String password = requestBody.get("password");

        // Validate that the password is not null or empty
        if (password == null || password.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null); // Return 400 Bad Request if the password is missing
        }

        Optional<UserAccountDto> updatedAccountOpt = userAccountService.updateAccountPassword(id, password);
        return updatedAccountOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
        userAccountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserAccountDto loginDto) {
        Optional<UserAccountDto> accountOpt = userAccountService.getAccountByEmail(loginDto.getEmail());

        if (accountOpt.isPresent()) {
            UserAccountDto account = accountOpt.get();

            // Verify the plaintext password
            if (userAccountService.verifyPassword(loginDto.getPassword(), account.getPassword())) {
                // Authentication successful
                return ResponseEntity.ok("Login successful");
            } else {
                // Incorrect password
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
            }
        } else {
            // Account not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }
}

