package com.kids_clothing.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupCustomerRequest {
    @Valid
    private SignupRequest signupRequest;

    private CustomerRequest customerRequest;
}
