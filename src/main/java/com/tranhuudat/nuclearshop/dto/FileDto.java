package com.tranhuudat.nuclearshop.dto;

import com.tranhuudat.nuclearshop.entity.File;
import com.tranhuudat.nuclearshop.util.ConstUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FileDto extends BaseDto{
    private Long id;
    private String fileName;
    private Long fileSize;
    private String description;

    public FileDto(File entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.fileName = entity.getFileName();
        this.fileSize = entity.getFileSize();
    }
    public String getDownloadUrl() {
        return ConstUtil.HOST_URL + ConstUtil.URL_PUBLIC_FILE_DOWNLOAD + getId();
    }
}
