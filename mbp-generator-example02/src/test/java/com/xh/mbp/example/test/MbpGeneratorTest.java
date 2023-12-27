package com.xh.mbp.example.test;

import com.xh.mbp.example.service.MbpGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MbpGeneratorTest {

    @Resource
    private MbpGeneratorService generatorService;


    @Test
    public void create() {

        generatorService.create(Arrays.asList("gateway_route", "gateway_route_item"));
    }

    @Test
    public void outputString() {
        //Map<String, String> map = generatorTemplate.outputString(Arrays.asList("gateway_route", "gateway_route_item"), null);
        //
        //for (Map.Entry<String, String> entry : map.entrySet()) {
        //    System.out.println(entry.getKey());
        //    System.out.println(entry.getValue());
        //
        //    System.out.println();
        //    System.out.println();
        //    System.out.println("------------------------------------");
        //    System.out.println();
        //    System.out.println();
        //}

    }
}
