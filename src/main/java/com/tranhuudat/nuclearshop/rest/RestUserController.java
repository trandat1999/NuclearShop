package com.tranhuudat.nuclearshop.rest;

import com.tranhuudat.nuclearshop.request.UserRequest;
import com.tranhuudat.nuclearshop.request.search.SearchRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/user")
@AllArgsConstructor
@Slf4j
public class RestUserController {

    private UserService userService;

    @PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody @Valid UserRequest user) {
        log.info("API create user");
        return ResponseEntity.ok(userService.save(user));
    }

    @PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> update(@RequestBody @Valid UserRequest user, @PathVariable("id") long id) {
        log.info("API update user");
        return ResponseEntity.ok(userService.update(user, id));
    }

    @PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> get(@PathVariable("id") long id) {
        log.info("API get user by id : {}", id);
        return ResponseEntity.ok(userService.get(id));
    }

    @PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
    @GetMapping()
    public ResponseEntity<BaseResponse> getAll() {
        log.info("API get all user");
        return ResponseEntity.ok(userService.getAll());
    }

    @PreAuthorize("hasAnyAuthority(T(com.tranhuudat.nuclearshop.util.ConstUtil).ADMIN_ROLE)")
    @PostMapping("/get-page")
    public ResponseEntity<BaseResponse> getPage(@RequestBody SearchRequest searchRequest) {
        log.info("API get page user");
        return ResponseEntity.ok(userService.getPage(searchRequest));
    }

    @GetMapping("/current")
    public ResponseEntity<BaseResponse> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

}
