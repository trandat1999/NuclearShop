package com.tranhuudat.nuclearshop.util;

import com.deepoove.poi.XWPFTemplate;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class WordUtils {

    public static XWPFTemplate exportWord(){
        try {
            Map<String,Object> rs = new HashMap<>();
            File file = ResourceUtils.getFile("classpath:templates/word/template.docx");
            XWPFDocument doc = new XWPFDocument(new FileInputStream(file));
            XWPFTemplate xwpfTemplate = XWPFTemplate.compile(doc);
            rs.put("name","data");
            xwpfTemplate.render(rs);
            return xwpfTemplate;
        } catch (IOException e) {
            return null;
        }
    }
}
