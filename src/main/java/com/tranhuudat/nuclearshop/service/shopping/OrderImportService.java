package com.tranhuudat.nuclearshop.service.shopping;

import com.tranhuudat.nuclearshop.dto.search.OrderImportSearch;
import com.tranhuudat.nuclearshop.dto.search.WarehouseSearch;
import com.tranhuudat.nuclearshop.dto.shopping.OrderImportDto;
import com.tranhuudat.nuclearshop.dto.shopping.ProductImportDto;
import com.tranhuudat.nuclearshop.dto.shopping.WarehouseDto;
import com.tranhuudat.nuclearshop.entity.shopping.*;
import com.tranhuudat.nuclearshop.repository.shopping.OrderImportRepository;
import com.tranhuudat.nuclearshop.repository.shopping.ProductRepository;
import com.tranhuudat.nuclearshop.repository.shopping.PublisherRepository;
import com.tranhuudat.nuclearshop.repository.shopping.WarehouseRepository;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.BaseService;
import com.tranhuudat.nuclearshop.type.OrderImportStatus;
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

import java.util.*;

/**
 * @author DatNuclear on 03/01/2023
 * @project NuclearShop
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"OrderImport"})
public class OrderImportService extends BaseService {
    private final WarehouseRepository warehouseRepository;
    private final PublisherRepository publisherRepository;
    private final OrderImportRepository orderImportRepository;
    private final ProductRepository productRepository;
    @CachePut(key = "#p1",condition = "#p1!=null")
    @CacheEvict(allEntries = true)
    public BaseResponse saveOrUpdate(OrderImportDto request, Long id){
        OrderImport entity;
        if(!ObjectUtils.isEmpty(id)){
            entity = orderImportRepository.findById(id).orElse(null);
            if(ObjectUtils.isEmpty(entity)){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.ORDER_IMPORT));
            }else{
                entity.setStatus(request.getStatus());
            }
        }else{
            entity = new OrderImport();
            entity.setStatus(OrderImportStatus.NEW);
        }
        entity.setOrderDate(request.getOrderDate());
        entity.setStaffOrder(request.getStaffOrder());
        entity.setDateFinished(request.getDateFinished());
        entity.setStaffFinished(request.getStaffFinished());
        if(ObjectUtils.isEmpty(request.getPublisher()) || ObjectUtils.isEmpty(request.getPublisher().getId())){
            return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PUBLISHER));
        }else{
            Optional<Publisher> optionalPublisher = publisherRepository.findById(request.getPublisher().getId());
            if(optionalPublisher.isEmpty()){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PUBLISHER));
            }
            entity.setPublisher(optionalPublisher.get());
        }
        if(ObjectUtils.isEmpty(request.getWarehouse()) || ObjectUtils.isEmpty(request.getWarehouse().getId())){
            return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.WAREHOUSE));
        }else{
            Optional<Warehouse> warehouseOptional = warehouseRepository.findById(request.getWarehouse().getId());
            if(warehouseOptional.isEmpty()){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.WAREHOUSE));
            }
            entity.setWarehouse(warehouseOptional.get());
        }
        List<ProductImport> productImportList = new ArrayList<>();
        for(ProductImportDto productImportDto : request.getProducts()){
            ProductImport productImport = new ProductImport();
            productImport.setQuantity(productImportDto.getQuantity());
            productImport.setPrice(productImportDto.getPrice());
            productImport.setOrderImport(entity);
            if(ObjectUtils.isEmpty(productImportDto.getProduct()) || ObjectUtils.isEmpty(productImportDto.getProduct().getId())){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PRODUCT));
            }else{
                Optional<Product> productOptional = productRepository.findById(request.getWarehouse().getId());
                if(productOptional.isEmpty()){
                    return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.WAREHOUSE));
                }
                productImport.setProduct(productOptional.get());
            }
            productImportList.add(productImport);
        }
        entity.getProducts().clear();
        entity.getProducts().addAll(productImportList);
        entity = orderImportRepository.save(entity);
        return getResponse200(new OrderImportDto(entity),getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
    @Cacheable(key = "#id")
    public BaseResponse get(Long id){
        Optional<OrderImport> optional = orderImportRepository.findById(id);
        if(optional.isPresent()){
            return getResponse200(new OrderImportDto(optional.get()),
                    getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
    @CacheEvict(allEntries = true)
    public BaseResponse delete(Long id){
        Optional<OrderImport> optional = orderImportRepository.findById(id);
        if(optional.isPresent()){
            OrderImport entity = optional.get();
            if(!entity.getStatus().equals(OrderImportStatus.NEW)){
                return getResponse400(getMessage(SystemMessage.MESSAGE_CAN_NOT_DELETE,SystemVariable.ORDER_IMPORT));
            }
            entity.setVoided(true);
            entity = orderImportRepository.save(entity);
            return getResponse200(new OrderImportDto(entity),
                    getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.ORDER_IMPORT));
    }
    @Cacheable(key = "#search.hashCode()")
    public BaseResponse search(OrderImportSearch search){
        Page<OrderImportDto> page = orderImportRepository.getPage(search.getKeyword(),search.getFromDate(),
                search.getToDate(),search.getPublisherId(),search.getWarehouseId(),getPageable(search));
        return getResponse200(page,getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
}
