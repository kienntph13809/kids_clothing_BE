package com.kids_clothing.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymenDto {
    Long amount;//số tiền
    String description;//mỗ tả
    String bankcode;//ngân hàng
    String language;//ngôn ngữ
}
