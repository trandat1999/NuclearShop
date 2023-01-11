package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.dto.search.WarehouseSearch;
import com.tranhuudat.nuclearshop.dto.shopping.WarehouseDto;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.shopping.WarehouseService;
import com.tranhuudat.nuclearshop.util.anotation.LogUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author DatNuclear on 11/01/2023
 * @project NuclearShop
 */
@PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
@RequestMapping("/api/v1/warehouses")
@RestController
@RequiredArgsConstructor
public class RestWarehouseController {
    private final WarehouseService warehouseService;
    @GetMapping
    @LogUsername(m="Api warehouse get all")
    public ResponseEntity<BaseResponse> getAll(){
        return ResponseEntity.ok(warehouseService.getAll());
    }
    @GetMapping("/{id}")
    @LogUsername(m="Api warehouse get")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(warehouseService.get(id));
    }
    @DeleteMapping("/{id}")
    @LogUsername(m="Api warehouse delete")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(warehouseService.delete(id));
    }
    @PutMapping("/{id}")
    @LogUsername(m="Api warehouse put")
    public ResponseEntity<BaseResponse> put(@PathVariable("id") Long id, @RequestBody @Valid WarehouseDto request){
        return ResponseEntity.ok(warehouseService.saveOrUpdate(request,id));
    }
    @PostMapping
    @LogUsername(m="Api warehouse post")
    public ResponseEntity<BaseResponse> post(@RequestBody @Valid WarehouseDto request){
        return ResponseEntity.ok(warehouseService.saveOrUpdate(request,null));
    }
    @PostMapping("/pages")
    @LogUsername(m="Api warehouse page")
    public ResponseEntity<BaseResponse> search(@RequestBody WarehouseSearch search){
        return ResponseEntity.ok(warehouseService.search(search));
    }
}
