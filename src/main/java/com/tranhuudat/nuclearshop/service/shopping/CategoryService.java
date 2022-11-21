package com.tranhuudat.nuclearshop.service.shopping;

import com.tranhuudat.nuclearshop.entity.shopping.Category;
import com.tranhuudat.nuclearshop.repository.shopping.CategoryRepository;
import com.tranhuudat.nuclearshop.request.search.CategorySearchRequest;
import com.tranhuudat.nuclearshop.request.shopping.CategoryRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.FileResponse;
import com.tranhuudat.nuclearshop.response.shopping.CategoryResponse;
import com.tranhuudat.nuclearshop.service.BaseService;
import com.tranhuudat.nuclearshop.util.CommonUtils;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService extends BaseService {
    private CategoryRepository categoryRepository;

    public BaseResponse saveOrUpdate(CategoryRequest categoryRequest, Long id){
        TypedParameterValue code = new TypedParameterValue(StringType.INSTANCE,categoryRequest.getCode());
        TypedParameterValue idCategory = new TypedParameterValue(LongType.INSTANCE,id);
        long countByCodeExists = categoryRepository.checkCode(code,idCategory);
        if(countByCodeExists>0){
            Map<String, String> errors = new HashMap<>();
            errors.put(SystemVariable.CODE, getMessage(SystemMessage.MESSAGE_ERROR_EXISTS,categoryRequest.getCode()));
            return getResponse400(SystemMessage.MESSAGE_BAD_REQUEST,errors);
        }
        if(CommonUtils.isNotNull(id)){
            Optional<Category> optionalCategory = categoryRepository.findById(id);
            if(optionalCategory.isEmpty()){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.CATEGORY));
            }
        }
        if(CommonUtils.isNotNull(categoryRequest.getParentId())){
            Optional<Category> optionalCategory = categoryRepository.findById(categoryRequest.getParentId());
            if(optionalCategory.isEmpty()){
                return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE, SystemVariable.CATEGORY_PARENT));
            }
        }
        Category entity = Category.builder()
                .code(categoryRequest.getCode())
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .parentId(categoryRequest.getParentId())
                .id(id)
                .build();
        entity = categoryRepository.save(entity);
        CategoryResponse categoryResponse = projectionFactory.createProjection(CategoryResponse.class, entity);
        return getResponse200(categoryResponse,getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }

    public BaseResponse getPage(CategorySearchRequest request){
        TypedParameterValue keyword = new TypedParameterValue(StringType.INSTANCE,request.getKeyword());
        return getResponse200(categoryRepository.findPage(keyword,getPageable(request)),getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }

    public BaseResponse delete(long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            categoryRepository.delete(optionalCategory.get());
            return getResponse200(id,getMessage(SystemMessage.MESSAGE_DELETE_SUCCESS,SystemVariable.CATEGORY));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.CATEGORY));
    }

    public BaseResponse get(long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isPresent()){
            return getResponse200(projectionFactory.createProjection(CategoryResponse.class,optionalCategory.get()),
                    getMessage(SystemMessage.MESSAGE_FOUND,SystemVariable.CATEGORY));
        }
        return getResponse400(getMessage(SystemMessage.MESSAGE_NOT_FOUND_IN_DATABASE,SystemVariable.CATEGORY));
    }

    public BaseResponse getAllParent(){
        return getResponse200(categoryRepository.findAllParent(),
                getMessage(SystemMessage.MESSAGE_FOUND,SystemVariable.CATEGORY));
    }
}
