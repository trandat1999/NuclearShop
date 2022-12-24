package com.tranhuudat.nuclearshop.service.shopping;

import com.tranhuudat.nuclearshop.entity.File;
import com.tranhuudat.nuclearshop.entity.shopping.Category;
import com.tranhuudat.nuclearshop.entity.shopping.Product;
import com.tranhuudat.nuclearshop.repository.FileRepository;
import com.tranhuudat.nuclearshop.repository.shopping.CategoryRepository;
import com.tranhuudat.nuclearshop.repository.shopping.ProductRepository;
import com.tranhuudat.nuclearshop.request.FileRequest;
import com.tranhuudat.nuclearshop.request.search.ProductSearch;
import com.tranhuudat.nuclearshop.request.shopping.CategoryRequest;
import com.tranhuudat.nuclearshop.request.shopping.ProductRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.shopping.ProductResponse;
import com.tranhuudat.nuclearshop.service.BaseService;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProductService extends BaseService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;

    public BaseResponse get(Long id){
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isPresent()) {
            return getResponse200(projectionFactory.createNullableProjection(ProductResponse.class,optional.get()),
                    getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
        }
        return getResponse204(null,getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PRODUCT));
    }

    public BaseResponse delete(Long id){
        Optional<Product> optional = productRepository.findById(id);
        if(optional.isPresent()) {
            Product  entity = optional.get();
            entity.setVoided(true);
            entity = productRepository.save(entity);
            return getResponse200(projectionFactory.createNullableProjection(ProductResponse.class,entity),
                    getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
        }
        return getResponse204(null,getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.PRODUCT));
    }

    public BaseResponse saveOrUpdate(ProductRequest request, Long id){
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
            for(CategoryRequest categoryRequest : request.getCategories()){
                category = null;
                if(!ObjectUtils.isEmpty(categoryRequest.getId())){
                    category = categoryRepository.findById(categoryRequest.getId()).orElse(null);
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
            for(FileRequest fileRequest : request.getFiles()){
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
        return getResponse200(projectionFactory.createNullableProjection(ProductResponse.class,entity),
                getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
    }

    public BaseResponse getPage(ProductSearch search){
        Pageable pageable = getPageable(search);
        Page<ProductResponse> page = productRepository.getPage(search.getKeyword(),search.getVoided(),  pageable);
        return getResponse200(page,getMessage(SystemMessage.MESSAGE_FOUND, SystemVariable.PRODUCT));
    }
}
