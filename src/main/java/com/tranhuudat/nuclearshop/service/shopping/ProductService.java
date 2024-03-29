package com.tranhuudat.nuclearshop.service.shopping;

import com.tranhuudat.nuclearshop.entity.File;
import com.tranhuudat.nuclearshop.entity.shopping.Category;
import com.tranhuudat.nuclearshop.entity.shopping.Product;
import com.tranhuudat.nuclearshop.repository.FileRepository;
import com.tranhuudat.nuclearshop.repository.shopping.CategoryRepository;
import com.tranhuudat.nuclearshop.repository.shopping.ProductRepository;
import com.tranhuudat.nuclearshop.dto.FileDto;
import com.tranhuudat.nuclearshop.dto.search.ProductSearch;
import com.tranhuudat.nuclearshop.dto.shopping.CategoryDto;
import com.tranhuudat.nuclearshop.dto.shopping.ProductDto;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.BaseService;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"Product"})
public class ProductService extends BaseService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;

    @Cacheable(key = "#id")
    public BaseResponse get(Long id){
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isPresent()) {
            return getResponse200(new ProductDto(optional.get()),
                    getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
        }
        return getResponse204(null,getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PRODUCT));
    }
    @CacheEvict(key = "#id")
    public BaseResponse delete(Long id){
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isPresent()) {
            Product  entity = optional.get();
            entity.setVoided(true);
            entity = productRepository.save(entity);
            return getResponse200(new ProductDto(entity),
                    getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
        }
        return getResponse204(null,getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PRODUCT));
    }

    @CachePut(key = "#p1",condition = "#p1!=null")
    @CacheEvict(allEntries = true)
    public BaseResponse saveOrUpdate(ProductDto request, Long id){
        long countExistCode = productRepository.countExitsCode(request.getCode(),id);
        if(countExistCode>0){
            Map<String,String> errors = Collections.singletonMap(SystemVariable.CODE, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS, request.getCode()));
            return getResponse400(SystemMessage.MESSAGE_BAD_REQUEST,errors);
        }
        Product entity = null;
        if(!ObjectUtils.isEmpty(id)){
            entity = productRepository.findById(id).orElse(null);
            if(entity == null){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PRODUCT));
            }
        }else{
            entity = new Product();
        }
        if(!CollectionUtils.isEmpty(request.getCategories())){
            Set<Category> categories = new HashSet<>();
            Category category;
            for(CategoryDto categoryDto : request.getCategories()){
                category = null;
                if(!ObjectUtils.isEmpty(categoryDto.getId())){
                    category = categoryRepository.findById(categoryDto.getId()).orElse(null);
                }
                if(!ObjectUtils.isEmpty(category)){
                    categories.add(category);
                }
            }
            entity.getCategories().clear();
            entity.getCategories().addAll(categories);
        }else{
            entity.getCategories().clear();
        }
        if(!CollectionUtils.isEmpty(request.getFiles())){
            Set<File> files = new HashSet<>();
            File file;
            for(FileDto fileRequest : request.getFiles()){
                file = null;
                if(!ObjectUtils.isEmpty(fileRequest.getId())){
                    file = fileRepository.findById(fileRequest.getId()).orElse(null);
                }
                if(!ObjectUtils.isEmpty(file)){
                    files.add(file);
                }
            }
            entity.getFiles().clear();
            entity.getFiles().addAll(files);
        }else{
            entity.getFiles().clear();
        }
        entity.setName(request.getName());
        entity.setCode(request.getCode());
        entity.setDescription(request.getDescription());
        entity.setShortDescription(request.getShortDescription());
        entity.setVoided(request.getVoided());
        entity = productRepository.save(entity);
        return getResponse200(new ProductDto(entity),
                getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
    }

    @Cacheable(key = "#search.hashCode()")
    public BaseResponse getPage(ProductSearch search){
        Pageable pageable = getPageable(search);
        Page<ProductDto> page = productRepository.getPage(search.getKeyword(),search.getVoided(),  pageable);
        return getResponse200(page,getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
    }

    @Cacheable
    public BaseResponse getAll(){
        List<ProductDto> rs = productRepository.getAll();
        return getResponse200(rs,getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }
}
