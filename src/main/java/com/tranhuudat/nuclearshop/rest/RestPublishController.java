package com.tranhuudat.nuclearshop.rest;

import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.FileResponse;
import com.tranhuudat.nuclearshop.service.FileService;
import com.tranhuudat.nuclearshop.util.FileUtils;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/publish")
@AllArgsConstructor
@Slf4j
public class RestPublishController extends BaseRestController{
    private FileService fileService;

    @GetMapping(value = "/files/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable("id") Long id){
        log.info("Downloading file {}",id);
        BaseResponse response = fileService.getFile(id);
        if(response.getBody() != null){
            FileResponse fileResponse = (FileResponse) response.getBody();
            Resource resource = FileUtils.getFile(fileResponse.getPath());
            if(ObjectUtils.isEmpty(resource)){
                return ResponseEntity.ok(getResponse404(getMessage(SystemMessage.MESSAGE_FILE_NOT_FOUND)));
            }
            String headerValue = "attachment; filename=\"" + fileResponse.getFileName() + "\"";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .contentLength(fileResponse.getFileSize())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        }
        return ResponseEntity.ok(response);
    }
}
