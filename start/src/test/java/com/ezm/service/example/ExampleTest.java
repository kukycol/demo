package com.ezm.service.example;

import com.ezm.StartApplication;
import com.ezm.common.response.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = StartApplication.class)
public class ExampleTest {

    @Resource
    private ExampleService exampleService;

    @Test
    public void test01(){
        Result all = exampleService.findAll(1, 2);
        System.out.println(all);
    }





}
