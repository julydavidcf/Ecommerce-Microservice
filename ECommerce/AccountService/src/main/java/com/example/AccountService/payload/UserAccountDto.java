package com.example.AccountService.payload;
import com.example.AccountService.entity.UserAccount;

public class UserAccountDto {

    private Long id;
    private String email;
    private String username;
    private String password;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;

    // Default constructor
    public UserAccountDto() {}

    // Parameterized constructor
    public UserAccountDto(Long id, String email, String username, String password, String shippingAddress, String billingAddress, String paymentMethod) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Methods to convert between Entity and DTO
    public static UserAccountDto fromEntity(UserAccount userAccount) {
        return new UserAccountDto(
                userAccount.getId(),
                userAccount.getEmail(),
                userAccount.getUsername(),
                userAccount.getPassword(),
                userAccount.getShippingAddress(),
                userAccount.getBillingAddress(),
                userAccount.getPaymentMethod()
        );
    }

    public static UserAccount toEntity(UserAccountDto userAccountDTO) {
        UserAccount userAccount = new UserAccount();
        userAccount.setId(userAccountDTO.getId());
        userAccount.setEmail(userAccountDTO.getEmail());
        userAccount.setUsername(userAccountDTO.getUsername());
        userAccount.setPassword(userAccountDTO.getPassword());
        userAccount.setShippingAddress(userAccountDTO.getShippingAddress());
        userAccount.setBillingAddress(userAccountDTO.getBillingAddress());
        userAccount.setPaymentMethod(userAccountDTO.getPaymentMethod());
        return userAccount;
    }
}

