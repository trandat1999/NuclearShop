package com.tranhuudat.nuclearshop.util;

import com.tranhuudat.nuclearshop.dto.shopping.AdministrativeUnitDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class ImportExcelUtils {

    public static List<AdministrativeUnitDto> loadDataFromFile(MultipartFile multipart){
        if(CommonUtils.isNull(multipart) || !StringUtils.hasText(multipart.getOriginalFilename())){
            return null;
        }
        HashMap<String, AdministrativeUnitDto> hashAdministrativeUnitMap = new HashMap<>();
        Workbook workbook = null;
        try {
            if(multipart.getOriginalFilename().endsWith(".xls")){
                workbook = new HSSFWorkbook(multipart.getInputStream());
            }else if(multipart.getOriginalFilename().endsWith(".xlsx")){
                workbook = new XSSFWorkbook(multipart.getInputStream());
            }else{
                return null;
            }
        }catch (Exception e){
            return null;
        }
        Sheet sheet = workbook.getSheetAt(0);
        Row row = null;
        Cell cell = null;
        int rowIndex = 1;
        String codeProvince = "";
        String nameProvince = "";
        String codeDistrict = "";
        String nameDistrict = "";
        String codeWard = "";
        String nameWard = "";
        while (rowIndex <= sheet.getLastRowNum()){
            row = sheet.getRow(rowIndex++);
            if(row!=null){
                cell = row.getCell(1);
                codeProvince = cell.getStringCellValue();
                cell = row.getCell(0);
                nameProvince = cell.getStringCellValue();
                cell = row.getCell(3);
                codeDistrict = cell.getStringCellValue();
                cell = row.getCell(2);
                nameDistrict = cell.getStringCellValue();
                cell = row.getCell(5);
                codeWard = cell.getStringCellValue();
                cell = row.getCell(4);
                nameWard = cell.getStringCellValue();
                if(!hashAdministrativeUnitMap.containsKey(codeProvince)){
                    hashAdministrativeUnitMap.put(codeProvince, AdministrativeUnitDto.builder()
                                    .code(codeProvince)
                                    .name(nameProvince)
                            .build());
                }
                if(!hashAdministrativeUnitMap.containsKey(codeDistrict)){
                    hashAdministrativeUnitMap.put(codeDistrict, AdministrativeUnitDto.builder()
                            .code(codeDistrict)
                            .name(nameDistrict)
                            .parentCode(codeProvince)
                            .build());
                }
                if(!hashAdministrativeUnitMap.containsKey(codeWard)){
                    hashAdministrativeUnitMap.put(codeWard, AdministrativeUnitDto.builder()
                            .code(codeWard)
                            .name(nameWard)
                            .parentCode(codeDistrict)
                            .build());
                }
            }
        }
        return new ArrayList<>(hashAdministrativeUnitMap.values());
    }
}
