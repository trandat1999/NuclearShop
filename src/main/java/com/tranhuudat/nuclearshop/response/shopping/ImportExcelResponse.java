package com.tranhuudat.nuclearshop.response.shopping;

import lombok.Data;

@Data
public class ImportExcelResponse {
    private Object successfully;
    private Integer totalRecords;
    private Integer successRecords;
    private Integer failedRecords;
    private Object errorRecords;
}
