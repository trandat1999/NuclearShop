package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.request.search.SearchRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.rest.BaseRestController;
import com.tranhuudat.nuclearshop.service.shopping.AdministrativeUnitService;
import com.tranhuudat.nuclearshop.util.anotation.LogUsername;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/api/v1/administrative-units")
public class RestAdministrativeUnitController extends BaseRestController {

    private AdministrativeUnitService administrativeUnitService;

    @PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
    @PostMapping("/import-excel")
    @LogUsername(m = "Api import excel administrative unit")
    public ResponseEntity<BaseResponse> importData(@RequestParam("file") MultipartFile multipartFile){
        return ResponseEntity.ok(administrativeUnitService.importData(multipartFile));
    }

    @LogUsername(m = "Api get page administrative unit")
    @PostMapping("/page")
    public ResponseEntity<BaseResponse> getPageParent(@RequestBody SearchRequest request){
        return ResponseEntity.ok(administrativeUnitService.getPageParent(request));
    }

    @LogUsername(m = "Api get all parent administrative unit")
    @GetMapping("/all-parent")
    public ResponseEntity<BaseResponse> getAllParent(){
        return ResponseEntity.ok(administrativeUnitService.getAllParent());
    }

    @LogUsername(m = "Api get all by parent administrative unit")
    @GetMapping("/all-by-parent/{id}")
    public ResponseEntity<BaseResponse> getAllByParent(@PathVariable("id") long id){
        return ResponseEntity.ok(administrativeUnitService.getAllByParent(id));
    }
}
