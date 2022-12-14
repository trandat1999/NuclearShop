package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.dto.search.CategorySearchRequest;
import com.tranhuudat.nuclearshop.dto.shopping.CategoryDto;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.shopping.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
@RequestMapping("/api/v1/categories")
public class RestCategoryController {
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<BaseResponse> save(@Valid @RequestBody CategoryDto request){
        return ResponseEntity.ok(categoryService.saveOrUpdate(request));
    }

    @PostMapping("/search")
    public ResponseEntity<BaseResponse> search(@RequestBody CategorySearchRequest request){
        return ResponseEntity.ok(categoryService.getPage(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") long id){
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping ("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") long id){
        return ResponseEntity.ok(categoryService.get(id));
    }

    @GetMapping ("/parent")
    public ResponseEntity<BaseResponse> getParent(){
        return ResponseEntity.ok(categoryService.getAllParent());
    }

    @GetMapping
    public ResponseEntity<BaseResponse> save(){
        return ResponseEntity.ok(categoryService.getAll());
    }

}
