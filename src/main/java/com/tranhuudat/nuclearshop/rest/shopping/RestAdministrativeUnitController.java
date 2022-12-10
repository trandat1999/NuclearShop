package com.tranhuudat.nuclearshop.rest.shopping;

import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.rest.BaseRestController;
import com.tranhuudat.nuclearshop.service.shopping.AdministrativeUnitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<BaseResponse> importData(@RequestParam("file") MultipartFile multipartFile){
        return ResponseEntity.ok(administrativeUnitService.importData(multipartFile));
    }
}
