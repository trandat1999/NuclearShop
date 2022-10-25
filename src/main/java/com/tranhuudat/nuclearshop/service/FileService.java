package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.entity.File;
import com.tranhuudat.nuclearshop.repository.FileRepository;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.FileResponse;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import com.tranhuudat.nuclearshop.util.FileUtils;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import com.tranhuudat.nuclearshop.util.SystemVariable;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FileService extends BaseService{
    private FileRepository fileRepository;
    private MessageSource messageSource;

    public BaseResponse save(MultipartFile multipartFile){
        String randomUUID = UUID.randomUUID().toString();
        String filename = multipartFile.getOriginalFilename();
        if(!StringUtils.hasText(filename)){
            return getResponse400(getMessage(SystemMessage.MESSAGE_FILE_NAME_INVALID));
        }
        int dot = filename.lastIndexOf(".");
        String firstString = filename.substring(0,dot);
        String fileExtension = filename.substring(dot);
        String filePath = firstString + ConstUtil.DASH + randomUUID + fileExtension;
        filePath = ConstUtil.FILE_PATH_IMAGE + filePath;
        try {
            FileOutputStream stream  = new FileOutputStream(filePath);
            stream.write(multipartFile.getBytes());
            stream.close();
        } catch (IOException e) {
            return getResponse500(getMessage(SystemMessage.MESSAGE_WRITE_FILE_ERROR, e.getMessage()));
        }
        File file = File.builder()
                .fileSize(multipartFile.getSize())
                .fileName(multipartFile.getOriginalFilename())
                .path(filePath)
                .description(multipartFile.getContentType())
                .build();
        file = fileRepository.save(file);
        ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
        FileResponse fileResponse = projectionFactory.createProjection(FileResponse.class, file);
        return getResponse201(fileResponse,getMessage(SystemMessage.MESSAGE_CREATE_SUCCESS, SystemVariable.FILE));
    }

    public BaseResponse getFile(Long id){
        Optional<File> fileOptional = fileRepository.findById(id);
        if(fileOptional.isPresent()){
            File file = fileOptional.get();
            ProjectionFactory projectionFactory = new SpelAwareProxyProjectionFactory();
            FileResponse fileResponse = projectionFactory.createProjection(FileResponse.class, file);
            return getResponse200(fileResponse,getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
        }
        return getResponse404(getMessage(SystemMessage.MESSAGE_FILE_NOT_FOUND));
    }
}
