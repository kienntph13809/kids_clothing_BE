package com.kids_clothing.model.request;

import com.kids_clothing.entity.Product;
import com.kids_clothing.entity.Property;
import com.kids_clothing.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDto {
    private List<Product> product;

    private Property property;

    private Size size;

    private Double quantity;
}
