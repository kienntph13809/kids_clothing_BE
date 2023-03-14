package com.kids_clothing.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VnpayDto {
    Long amount;//số tiền
    String description;//mỗ tả
    Integer status;//trạng thái
}
