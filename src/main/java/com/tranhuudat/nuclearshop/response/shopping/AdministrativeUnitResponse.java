package com.tranhuudat.nuclearshop.response.shopping;

import org.springframework.util.ObjectUtils;

public interface AdministrativeUnitResponse {
    String getName();
    String getCode();
    Long getId();
    Long getParentId();
    String getParentCode();
    String getParentName();

    default AdministrativeUnitParent getParent(){
        if(ObjectUtils.isEmpty(getParentId())){
            return null;
        }
        return new AdministrativeUnitParent(){
            @Override
            public String getName() {
                return getParentName();
            }

            @Override
            public String getCode() {
                return getParentCode();
            }

            @Override
            public Long getId() {
                return getParentId();
            }
        };
    };

    interface AdministrativeUnitParent {
        String getName();
        String getCode();
        Long getId();
    }
}
