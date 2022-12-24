package com.tranhuudat.nuclearshop.request.search;

import lombok.Data;

import java.util.List;

@Data
public class ProductSearch extends SearchRequest{
    private List<Long> categoryIds;
}
