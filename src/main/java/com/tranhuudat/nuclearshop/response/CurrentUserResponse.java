package com.tranhuudat.nuclearshop.response;

import com.tranhuudat.nuclearshop.entity.File;

import java.util.Set;

public interface CurrentUserResponse {

    User getUser();

    interface User {
        String getUsername();

        Set<RoleResponse> getRoles();

        Person getPerson();
    }

    interface Person {
        String getLastName();

        String getFirstName();

        FileResponse getPhotoFile();
    }
}
