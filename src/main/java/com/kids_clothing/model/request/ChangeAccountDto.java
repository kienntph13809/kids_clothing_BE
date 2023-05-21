package com.kids_clothing.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeAccountDto {
    private Long id;
    private String fullname;
    private String email;
    private String username;
    private String sdt;
    private String address;
}
