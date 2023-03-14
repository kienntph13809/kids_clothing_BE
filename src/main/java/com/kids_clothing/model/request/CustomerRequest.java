package com.kids_clothing.model.request;

import com.kids_clothing.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String fullname;

    private Account account;
}
