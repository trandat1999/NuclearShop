package com.tranhuudat.nuclearshop.response;

import com.tranhuudat.nuclearshop.util.ConstUtil;

public interface FileResponse {
    Long getId();
    String getFileName();
    Long getFileSize();
    String getDescription();
    String getPath();
    default String getDownloadUrl(){
        return ConstUtil.HOST_URL+ConstUtil.URL_PUBLIC_FILE_DOWNLOAD+getId();
    };
}
