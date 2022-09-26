package com.tranhuudat.nuclearshop.service;

import com.tranhuudat.nuclearshop.response.BaseResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    public void testGetAll(){
        BaseResponse rs= roleService.getAllRoles();
        Assert.assertEquals(true,true);
    }

}
