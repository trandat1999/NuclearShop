package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.dto.search.ProductSearch;
import com.tranhuudat.nuclearshop.dto.shopping.ProductDto;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.shopping.ProductService;
import com.tranhuudat.nuclearshop.util.anotation.LogUsername;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
@RequestMapping("/api/v1/products")
public class RestProductController {

    private final ProductService productService;
    @PostMapping
    @LogUsername(m = "Api save product")
    public ResponseEntity<BaseResponse> save(@RequestBody @Valid ProductDto request){
        return ResponseEntity.ok(productService.saveOrUpdate(request,null));
    }
    @GetMapping("/{id}")
    @LogUsername(m = "Api get product")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.get(id));
    }

    @PutMapping("/{id}")
    @LogUsername(m = "Api put product")
    public ResponseEntity<BaseResponse> put(@PathVariable("id") Long id, @RequestBody @Valid ProductDto request){
        return ResponseEntity.ok(productService.saveOrUpdate(request,id));
    }
    @DeleteMapping("/{id}")
    @LogUsername(m = "Api delete product")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.delete(id));
    }

    @PostMapping("/pages")
    @LogUsername(m = "Api get pages product")
    public ResponseEntity<BaseResponse> search(@RequestBody ProductSearch search){
        return ResponseEntity.ok(productService.getPage(search));
    }
}
