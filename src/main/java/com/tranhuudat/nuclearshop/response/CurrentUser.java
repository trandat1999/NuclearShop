package com.tranhuudat.nuclearshop.response;

import java.util.Set;

public interface CurrentUser {

    User getUser();

    interface User {
        String getUsername();

        Set<RoleResponse> getRoles();

        Person getPerson();
    }

    interface Person {
        String getLastName();

        String getFirstName();
    }
}
