package com.kids_clothing.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SizeRequest {

    private String name;

    private Long idtypesize;

}
