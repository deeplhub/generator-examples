package com.xh.mbp.example.service;

import com.xh.mbp.generator.template.MbpGeneratorTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Component
public class MbpGeneratorService {

    @Resource
    private MbpGeneratorTemplate generatorTemplate;


    public void create(List<String> listTable) {
        System.out.println("1111111111111111111111111");
        generatorTemplate.create(listTable, null);
    }
}
