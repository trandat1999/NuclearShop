package com.tranhuudat.nuclearshop.response.shopping;

import java.util.Set;

public interface CategoryResponse {
    String getName();
    String getCode();
    Long getId();
    String getDescription();
    Set<CategoryResponse> getChildren();
}
