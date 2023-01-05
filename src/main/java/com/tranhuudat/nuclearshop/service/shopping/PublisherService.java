package com.tranhuudat.nuclearshop.service.shopping;

import com.tranhuudat.nuclearshop.dto.search.PublisherSearch;
import com.tranhuudat.nuclearshop.dto.shopping.PublisherDto;
import com.tranhuudat.nuclearshop.entity.shopping.Category;
import com.tranhuudat.nuclearshop.entity.shopping.Publisher;
import com.tranhuudat.nuclearshop.repository.shopping.AdministrativeUnitRepository;
import com.tranhuudat.nuclearshop.repository.shopping.PublisherRepository;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.shopping.CategoryViewResponse;
import com.tranhuudat.nuclearshop.service.BaseService;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Service
@RequiredArgsConstructor
public class PublisherService extends BaseService {
    private final PublisherRepository publisherRepository;
    private final AdministrativeUnitRepository administrativeUnitRepository;
    @CachePut(value = "Publisher", key = "#p1",condition = "#p1!=null")
    @CacheEvict(value = "Publisher",allEntries = true)
    public BaseResponse saveOrUpdate(PublisherDto request,Long id){
        long countExistCode = publisherRepository.countExitsCode(request.getCode(),id);
        if(countExistCode>0){
            Map<String,String> errors = Collections.singletonMap(SystemVariable.CODE, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS, request.getCode()));
            return getResponse400(SystemMessage.MESSAGE_BAD_REQUEST,errors);
        }
        Publisher entity;
        if(!ObjectUtils.isEmpty(id)){
            entity = publisherRepository.findById(id).orElse(null);
            if(ObjectUtils.isEmpty(entity)){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PUBLISHER));
            }
        }else{
            entity = new Publisher();
        }
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setCode(request.getCode());
        entity.setAddress(request.getAddress());
        if(!ObjectUtils.isEmpty(request.getAdministrativeUnit()) && !ObjectUtils.isEmpty(request.getAdministrativeUnit().getId())){
            entity.setAdministrativeUnit(administrativeUnitRepository.findById(request.getAdministrativeUnit().getId()).orElse(null));
        }else{
            entity.setAdministrativeUnit(null);
        }
        entity = publisherRepository.save(entity);
        return getResponse200(new PublisherDto(entity),getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
    @Cacheable(value = "Publisher",key = "#id")
    public BaseResponse get(Long id){
        Optional<Publisher> optional = publisherRepository.findById(id);
        if(optional.isPresent()){
            return getResponse200(new PublisherDto(optional.get()),getMessage(SystemMessage.MESSAGE_FOUND,SystemVariable.PUBLISHER));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.PUBLISHER));
    }
    @CacheEvict(value = "Publisher",key = "#id")
    public BaseResponse delete(Long id){
        Optional<Publisher> optional = publisherRepository.findById(id);
        if(optional.isPresent()){
            Publisher entity = optional.get();
            entity.setVoided(true);
            entity = publisherRepository.save(entity);
            return getResponse200(new PublisherDto(entity),getMessage(SystemMessage.MESSAGE_FOUND,SystemVariable.PUBLISHER));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.PUBLISHER));
    }
    @Cacheable(value = "Publisher")
    public BaseResponse getAll(){
        return getResponse200(publisherRepository.getAll(),getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
    @Cacheable("Publisher")
    public BaseResponse search(PublisherSearch search){
        Page<PublisherDto> page = publisherRepository.getPage(search.getKeyword(), search.getVoided(),
                search.getProvinceId(),search.getDistrictId(),search.getCommuneId(),getPageable(search));
        return getResponse200(page,getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
    }
}
