package com.tranhuudat.nuclearshop.request.search;

import lombok.Data;

@Data
public class SearchRequest {

    protected Boolean voided;
    protected String keyword;
    protected Integer pageSize;
    protected Integer pageIndex;
}
