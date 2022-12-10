package com.tranhuudat.nuclearshop.service.shopping;


import com.tranhuudat.nuclearshop.entity.shopping.AdministrativeUnit;
import com.tranhuudat.nuclearshop.repository.shopping.AdministrativeUnitRepository;
import com.tranhuudat.nuclearshop.request.shopping.AdministrativeUnitRequest;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.shopping.ImportExcelResponse;
import com.tranhuudat.nuclearshop.service.BaseService;
import com.tranhuudat.nuclearshop.util.ImportExcelUtils;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdministrativeUnitService extends BaseService {
    private AdministrativeUnitRepository administrativeUnitRepository;

    public BaseResponse importData(MultipartFile multipartFile){
        List<AdministrativeUnitRequest> administrative = ImportExcelUtils.loadDataFromFile(multipartFile);
        if(ObjectUtils.isEmpty(administrative)){
            return getResponse400(SystemMessage.MESSAGE_BAD_REQUEST);
        }
        return getResponse200(saveDataImport(administrative),getMessage(SystemMessage.MESSAGE_SUCCESS_PROPERTIES));
    }


    private ImportExcelResponse saveDataImport(List<AdministrativeUnitRequest> administrative){
        ImportExcelResponse rs = new ImportExcelResponse();
        rs.setTotalRecords(administrative.size());
        List<AdministrativeUnitRequest> successfully = new ArrayList<>();
        List<AdministrativeUnitRequest> failed = new ArrayList<>();
        for(AdministrativeUnitRequest request : administrative){
            try {
                AdministrativeUnit entity = administrativeUnitRepository.findByCode(request.getCode());
                if(entity != null){
                    entity.setName(request.getName());
                    if(StringUtils.hasText(request.getParentCode())){
                        entity.setParent(administrativeUnitRepository.findByCode(request.getParentCode()));
                    }
                }else{
                    entity = new AdministrativeUnit();
                    entity.setName(request.getName());
                    entity.setCode(request.getCode());
                    if(StringUtils.hasText(request.getParentCode())){
                        entity.setParent(administrativeUnitRepository.findByCode(request.getParentCode()));
                    }
                }
                entity = administrativeUnitRepository.save(entity);
                successfully.add(request);
            }catch(Exception e){
                failed.add(request);
            }

        }
        rs.setSuccessfully(successfully);
        rs.setErrorRecords(failed);
        rs.setFailedRecords(failed.size());
        rs.setSuccessRecords(successfully.size());
        return rs;
    }
}
