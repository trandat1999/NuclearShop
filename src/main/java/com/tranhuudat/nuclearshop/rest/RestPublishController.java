package com.tranhuudat.nuclearshop.rest;

import com.deepoove.poi.XWPFTemplate;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.FileResponse;
import com.tranhuudat.nuclearshop.service.FileService;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import com.tranhuudat.nuclearshop.util.FileUtils;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.WordUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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

    @GetMapping(value = "/export-word")
    public ResponseEntity<?> exportWord(HttpServletResponse response) throws IOException {
        XWPFTemplate rs = WordUtils.exportWord();
        if (rs != null) {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=test.docx");
            response.setContentType(ConstUtil.MINE_TYPE_DOCX);
            rs.writeAndClose(response.getOutputStream());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
