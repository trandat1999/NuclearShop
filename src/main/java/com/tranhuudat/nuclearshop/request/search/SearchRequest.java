package com.tranhuudat.nuclearshop.request.search;

import lombok.Data;

@Data
public class SearchRequest {
    private String keyword;
    private Integer pageSize;
    private Integer pageIndex;
}
