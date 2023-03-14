package com.kids_clothing.model.request;

import com.kids_clothing.entity.Categorydetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CategoryRequest {

    private String name;
    @Column(name = "isDelete")
    private Boolean isDelete = false;
    List<Categorydetail> categorydetails;

}
