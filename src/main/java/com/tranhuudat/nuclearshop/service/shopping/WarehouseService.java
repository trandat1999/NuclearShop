package com.tranhuudat.nuclearshop.service.shopping;

import com.tranhuudat.nuclearshop.dto.search.SearchRequest;
import com.tranhuudat.nuclearshop.dto.search.WarehouseSearch;
import com.tranhuudat.nuclearshop.dto.shopping.PublisherDto;
import com.tranhuudat.nuclearshop.dto.shopping.WarehouseDto;
import com.tranhuudat.nuclearshop.entity.shopping.Publisher;
import com.tranhuudat.nuclearshop.entity.shopping.Warehouse;
import com.tranhuudat.nuclearshop.repository.shopping.AdministrativeUnitRepository;
import com.tranhuudat.nuclearshop.repository.shopping.WarehouseRepository;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.BaseService;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames={"Warehouse"})
public class WarehouseService extends BaseService {
    private final WarehouseRepository warehouseRepository;
    private final AdministrativeUnitRepository administrativeUnitRepository;
    @CachePut(key = "#p1",condition = "#p1!=null")
    @CacheEvict(allEntries = true)
    public BaseResponse saveOrUpdate(WarehouseDto request, Long id){
        long countExistCode = warehouseRepository.countExitsCode(request.getCode(),id);
        if(countExistCode>0){
            Map<String,String> errors = Collections.singletonMap(SystemVariable.CODE, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS, request.getCode()));
            return getResponse400(SystemMessage.MESSAGE_BAD_REQUEST,errors);
        }
        Warehouse entity;
        if(!ObjectUtils.isEmpty(id)){
            entity = warehouseRepository.findById(id).orElse(null);
            if(ObjectUtils.isEmpty(entity)){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.WAREHOUSE));
            }
        }else{
            entity = new Warehouse();
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setCode(request.getCode());
        entity.setAcreage(request.getAcreage());
        entity.setPhoneNumber(request.getPhoneNumber());
        entity.setAddress(request.getAddress());
        if(!ObjectUtils.isEmpty(request.getAdministrativeUnit()) && !ObjectUtils.isEmpty(request.getAdministrativeUnit().getId())){
            entity.setAdministrativeUnit(administrativeUnitRepository.findById(request.getAdministrativeUnit().getId()).orElse(null));
        }else{
            entity.setAdministrativeUnit(null);
        }
        entity = warehouseRepository.save(entity);
        return getResponse200(new WarehouseDto(entity),getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
    @Cacheable(key = "#id")
    public BaseResponse get(Long id){
        Optional<Warehouse> optional = warehouseRepository.findById(id);
        if(optional.isPresent()){
            return getResponse200(new WarehouseDto(optional.get()),
                    getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.WAREHOUSE));
    }
    @CacheEvict(allEntries = true)
    public BaseResponse delete(Long id){
        Optional<Warehouse> optional = warehouseRepository.findById(id);
        if(optional.isPresent()){
            Warehouse entity = optional.get();
            entity.setVoided(true);
            entity = warehouseRepository.save(entity);
            return getResponse200(new WarehouseDto(entity),
                    getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.WAREHOUSE));
    }
    @Cacheable(key = "#search.hashCode()")
    public BaseResponse search(WarehouseSearch search){
        Page<WarehouseDto> page = warehouseRepository.getPage(search.getKeyword(),search.getProvinceId()
                ,search.getDistrictId(),search.getCommuneId(),getPageable(search));
        return getResponse200(page,getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
    @Cacheable
    public BaseResponse getAll(){
        return getResponse200(warehouseRepository.getAll(),getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
}
