package com.tranhuudat.nuclearshop.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductSearch extends SearchRequest{
    private List<Long> categoryIds;
}
