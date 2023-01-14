package com.tranhuudat.nuclearshop.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author DatNuclear on 12/01/2023
 * @project NuclearShop
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderImportSearch extends SearchRequest{
    private Date fromDate;
    private Date toDate;
    private Long publisherId;
    private Long warehouseId;
}
