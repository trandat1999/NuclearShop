package com.tranhuudat.nuclearshop.response.shopping;

public interface CategoryResponse {
    String getName();
    String getCode();
    Long getId();
    String getDescription();
    Long getParentId();
    String getParentName();
}
