package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.dto.search.OrderImportSearch;
import com.tranhuudat.nuclearshop.dto.shopping.OrderImportDto;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.shopping.OrderImportService;
import com.tranhuudat.nuclearshop.util.anotation.LogUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author DatNuclear at 2/3/2023
 * @project NuclearShop
 */
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE," +
        "T(com.tranhuudat.nuclearshop.util.ConstUtil).STAFF_WAREHOUSE_ROLE,T(com.tranhuudat.nuclearshop.util.ConstUtil).SALE_ROLE)")
@RequestMapping(value = "/api/v1/order-imports")
public class RestOrderImportController {
    private final OrderImportService orderImportService;
    @LogUsername(m = "Api get page order imports")
    @PostMapping("/pages")
    public ResponseEntity<BaseResponse> getPageParent(@RequestBody OrderImportSearch search){
        return ResponseEntity.ok(orderImportService.search(search));
    }
    @LogUsername(m = "Api post order imports")
    @PostMapping()
    public ResponseEntity<BaseResponse> post(@RequestBody @Valid OrderImportDto request){
        return ResponseEntity.ok(orderImportService.saveOrUpdate(request,null));
    }
    @LogUsername(m = "Api put order imports")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> put(@RequestBody @Valid OrderImportDto request,@PathVariable("id") Long id){
        return ResponseEntity.ok(orderImportService.saveOrUpdate(request,id));
    }
    @LogUsername(m = "Api get order imports")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderImportService.get(id));
    }
    @LogUsername(m = "Api delete order imports")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderImportService.get(id));
    }
}
