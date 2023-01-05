package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.dto.search.PublisherSearch;
import com.tranhuudat.nuclearshop.dto.shopping.PublisherDto;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.shopping.PublisherService;
import com.tranhuudat.nuclearshop.util.anotation.LogUsername;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author DatNuclear on 04/01/2023
 * @project NuclearShop
 */
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
@RequestMapping("/api/v1/suppliers")
@RestController
public class RestPublisherController {
    private final PublisherService publisherService;
    @GetMapping
    @LogUsername(m="Api publisher get all")
    public ResponseEntity<BaseResponse> getAll(){
        return ResponseEntity.ok(publisherService.getAll());
    }
    @GetMapping("/{id}")
    @LogUsername(m="Api publisher get")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(publisherService.get(id));
    }
    @DeleteMapping("/{id}")
    @LogUsername(m="Api publisher delete")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(publisherService.delete(id));
    }
    @PutMapping("/{id}")
    @LogUsername(m="Api publisher put")
    public ResponseEntity<BaseResponse> put(@PathVariable("id") Long id, @RequestBody @Valid PublisherDto request){
        return ResponseEntity.ok(publisherService.saveOrUpdate(request,id));
    }
    @PostMapping
    @LogUsername(m="Api publisher post")
    public ResponseEntity<BaseResponse> post(@RequestBody @Valid PublisherDto request){
        return ResponseEntity.ok(publisherService.saveOrUpdate(request,null));
    }
    @PostMapping("/pages")
    @LogUsername(m="Api publisher page")
    public ResponseEntity<BaseResponse> search(@RequestBody PublisherSearch search){
        return ResponseEntity.ok(publisherService.search(search));
    }
}
