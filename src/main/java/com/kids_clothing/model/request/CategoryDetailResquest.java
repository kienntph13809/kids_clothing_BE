package com.kids_clothing.model.request;

import com.kids_clothing.entity.Category;
import com.kids_clothing.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CategoryDetailResquest {

    private Long id;

    private String name;

    private Boolean isDelete = false;

    private Category category;

    private List<Product> products;

}
