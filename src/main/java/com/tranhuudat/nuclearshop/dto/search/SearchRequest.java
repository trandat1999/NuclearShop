package com.tranhuudat.nuclearshop.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SearchRequest {

    protected Boolean voided;
    protected String keyword;
    protected Integer pageSize;
    protected Integer pageIndex;
}
