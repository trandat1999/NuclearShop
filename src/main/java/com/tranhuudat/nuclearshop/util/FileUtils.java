package com.tranhuudat.nuclearshop.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileUtils {
    
    public static Resource getFile(String  filePath){
        try {
            File file = new File(filePath);
            return new InputStreamResource(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
