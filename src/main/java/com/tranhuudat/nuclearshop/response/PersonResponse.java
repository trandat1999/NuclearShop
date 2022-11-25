package com.tranhuudat.nuclearshop.response;

import com.tranhuudat.nuclearshop.type.Gender;

import java.util.Date;

public interface PersonResponse {
    FileResponse getPhotoFile();

    String getEmail();

    String getFirstName();

    String getLastName();

    Gender getGender();

    String getPhoneNumber();

    Date getBirthDate();

    String getIdNumber();

    String getIdNumberIssueBy();

    Date getIdNumberIssueDate();
}
