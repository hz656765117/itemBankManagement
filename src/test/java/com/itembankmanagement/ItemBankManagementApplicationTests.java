package com.itembankmanagement;

import com.itembankmanagement.util.XWPRUNTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ItemBankManagementApplicationTests {

    @Resource
    private XWPRUNTest xWPRUNTest;

    @Test
    public void runTest(){
        xWPRUNTest.runTest();
    }
}
