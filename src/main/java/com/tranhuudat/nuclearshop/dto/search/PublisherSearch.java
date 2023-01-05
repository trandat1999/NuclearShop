package com.tranhuudat.nuclearshop.dto.search;

import lombok.Data;

/**
 * @author DatNuclear on 04/01/2023
 * @project NuclearShop
 */
@Data
public class PublisherSearch extends SearchRequest{
    private Long provinceId;
    private Long districtId;
    private Long communeId;
}
