package com.tranhuudat.nuclearshop.response.shopping;

import com.tranhuudat.nuclearshop.response.FileResponse;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

public interface ProductResponse {
    String getName();
    String getDescription();
    Long getId();
    String getCode();
    String getShortDescription();

    @Value("#{target.files}")
    Set<FileResponse> getFiles();

    @Value("#{target.categories}")
    Set<CategoryResponse> getCategories();

    Boolean isVoided();
}
