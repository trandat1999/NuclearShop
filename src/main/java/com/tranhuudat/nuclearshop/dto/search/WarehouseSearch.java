package com.tranhuudat.nuclearshop.dto.search;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DatNuclear on 11/01/2023
 * @project NuclearShop
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class WarehouseSearch extends SearchRequest{
    private Long provinceId;
    private Long districtId;
    private Long communeId;
}
