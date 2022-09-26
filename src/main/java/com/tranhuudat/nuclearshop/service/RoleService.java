package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.repository.RoleRepository;
import com.tranhuudat.nuclearshop.response.BaseResponse;
import com.tranhuudat.nuclearshop.response.RoleResponse;
import com.tranhuudat.nuclearshop.util.SystemMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public BaseResponse getAllRoles() {
        List<RoleResponse> roles = roleRepository.getAllRoles();
        return BaseResponse.<List<RoleResponse>>builder()
                .body(roles)
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .message(SystemMessage.MESSAGE_SUCCESS)
                .build();
    }
}
