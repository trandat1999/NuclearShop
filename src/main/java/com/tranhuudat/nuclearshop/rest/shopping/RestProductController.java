package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.request.search.ProductSearch;
import com.tranhuudat.nuclearshop.request.shopping.ProductRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.shopping.ProductService;
import com.tranhuudat.nuclearshop.util.anotation.LogUsername;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<BaseResponse> save(@RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(productService.saveOrUpdate(request,null));
    }
    @GetMapping("/{id}")
    @LogUsername(m = "Api get product")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.get(id));
    }

    @PutMapping("/{id}")
    @LogUsername(m = "Api put product")
    public ResponseEntity<BaseResponse> put(@PathVariable("id") Long id, @RequestBody @Valid ProductRequest request){
        return ResponseEntity.ok(productService.saveOrUpdate(request,id));
    }
    @DeleteMapping("/{id}")
    @LogUsername(m = "Api delete product")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.delete(id));
    }

    @PostMapping("/pages")
    public ResponseEntity<BaseResponse> search(@RequestBody ProductSearch search){
        return ResponseEntity.ok(productService.getPage(search));
    }
}
