package com.tranhuudat.nuclearshop.response;

import com.tranhuudat.nuclearshop.type.Gender;

import java.util.Date;
import java.util.Set;

public interface UserResponse {

    User getUser();

    interface User {
        Long getId();

        String getUsername();

        String getEmail();

        Boolean getEnabled();

        Set<RoleResponse> getRoles();

        Person getPerson();
    }

    interface Person {
        String getLastName();

        String getFirstName();

        Date getBirthDate();

        String getPhoneNumber();

        Gender getGender();

        String getIdNumber();
    }

}
