package com.tranhuudat.nuclearshop.rest;

import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/role")
@AllArgsConstructor
@Slf4j
@PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
public class RestRoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<BaseResponse> getRoles() {
        log.info("API get all roles");
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
