package com.tranhuudat.nuclearshop.request.search;


import lombok.Data;

@Data
public class CategorySearchRequest extends SearchRequest{
    private String name;
    private String code;
}
