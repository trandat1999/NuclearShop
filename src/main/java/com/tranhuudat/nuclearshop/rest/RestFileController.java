package com.tranhuudat.nuclearshop.rest;

import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.service.FileService;
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
@RequestMapping(value = "/api/v1/files")
@AllArgsConstructor
@Slf4j
@PreAuthorize("isAuthenticated()")
public class RestFileController {
    private final FileService fileService;

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestParam("file") MultipartFile multipartFile){
        log.info("API POST file");
        return ResponseEntity.ok(fileService.save(multipartFile));
    }
}
