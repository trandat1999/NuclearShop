package com.tranhuudat.nuclearshop.dto.search;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CategorySearchRequest extends SearchRequest{
    private String name;
    private String code;
}
