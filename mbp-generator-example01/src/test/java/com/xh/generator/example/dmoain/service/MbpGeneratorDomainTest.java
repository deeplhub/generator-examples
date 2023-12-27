package com.xh.generator.example.dmoain.service;

import cn.hutool.core.io.IoUtil;
import com.xh.generator.example.dmoain.model.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author H.Yang
 * @date 2023/12/26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MbpGeneratorDomainTest {

    @Resource
    private MbpGeneratorDomain domain;

    /**
     * 创建文件 - 最少参数
     */
    @Test
    public void create1() {
        GlobalConfig globalConfigBuild = GlobalConfig.builder()
                .sourcePath("D:/code")
                .build();

        PackageConfig packageConfigBuilder = PackageConfig.builder()
                .packagePath("com.xh.example")
                .resourcePath("D:/code")
                .build();

        StrategyConfig strategyConfigBuilder = StrategyConfig.builder()
                .tableNames(Arrays.asList("gateway_route", "gateway_route_item"))
                .build();

        TemplateConfig templateConfigBuilder = TemplateConfig.builder()
                .selectedOutputFiles(Arrays.asList("entity", "mapper", "xml"))
                .build();

        ConfigInfo config = ConfigInfo.builder()
                .globalConfig(globalConfigBuild)
                .packageConfig(packageConfigBuilder)
                .strategyConfig(strategyConfigBuilder)
                .templateConfig(templateConfigBuilder)
                .build();

        domain.create(config);
    }

    /**
     * 创建文件 - 全量参数
     */
    @Test
    public void create2() {
        GlobalConfig globalConfigBuild = GlobalConfig.builder()
                .author("Hyang")
                .disableOpenDir(true)
                .sourcePath("D:/code")
                .build();

        PackageConfig packageConfigBuilder = PackageConfig.builder()
                .packagePath("com.xh.example")
                .resourcePath("D:/code")
                .build();

        StrategyConfig strategyConfigBuilder = StrategyConfig.builder()
                .tableNames(Arrays.asList("gateway_route", "gateway_route_item"))
                .tablePrefixs(Arrays.asList("gateway_"))
                .enableTableFieldAnnotation(false)
                .build();

        TemplateConfig templateConfigBuilder = TemplateConfig.builder()
                .selectedOutputFiles(Arrays.asList("entity", "mapper", "xml"))
                .build();

        ConfigInfo config = ConfigInfo.builder()
                .globalConfig(globalConfigBuild)
                .packageConfig(packageConfigBuilder)
                .strategyConfig(strategyConfigBuilder)
                .templateConfig(templateConfigBuilder)
                .build();

        domain.create(config);
    }

    /**
     * 输出字符串 - 最少参数
     */
    @Test
    public void outputString1() {
        GlobalConfig globalConfigBuild = GlobalConfig.builder()
                .sourcePath("code")
                .build();

        PackageConfig packageConfigBuilder = PackageConfig.builder()
                .packagePath("com.xh.example")
                .resourcePath("code")
                .build();

        StrategyConfig strategyConfigBuilder = StrategyConfig.builder()
                .tableNames(Arrays.asList("gateway_route", "gateway_route_item"))
                .build();

        TemplateConfig templateConfigBuilder = TemplateConfig.builder()
                .selectedOutputFiles(Arrays.asList("entity", "mapper", "xml"))
                .build();

        ConfigInfo config = ConfigInfo.builder()
                .globalConfig(globalConfigBuild)
                .packageConfig(packageConfigBuilder)
                .strategyConfig(strategyConfigBuilder)
                .templateConfig(templateConfigBuilder)
                .build();

        Map<String, String> map = domain.outputString(config);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());

            System.out.println();
            System.out.println();
            System.out.println("------------------------------------");
            System.out.println();
            System.out.println();
        }
    }

    /**
     * 输出字符串 - 全量参数
     */
    @Test
    public void outputString2() {
        GlobalConfig globalConfigBuild = GlobalConfig.builder()
                .author("Hyang")
                .disableOpenDir(true)
                .sourcePath("code")
                .build();

        PackageConfig packageConfigBuilder = PackageConfig.builder()
                .packagePath("com.xh.example")
                .resourcePath("code")
                .build();

        StrategyConfig strategyConfigBuilder = StrategyConfig.builder()
                .tableNames(Arrays.asList("gateway_route", "gateway_route_item"))
                .tablePrefixs(Arrays.asList("gateway_"))
                .enableTableFieldAnnotation(false)
                .build();

        TemplateConfig templateConfigBuilder = TemplateConfig.builder()
                .selectedOutputFiles(Arrays.asList("entity", "mapper", "xml"))
                .build();

        ConfigInfo config = ConfigInfo.builder()
                .globalConfig(globalConfigBuild)
                .packageConfig(packageConfigBuilder)
                .strategyConfig(strategyConfigBuilder)
                .templateConfig(templateConfigBuilder)
                .build();

        Map<String, String> map = domain.outputString(config);

        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());

            System.out.println();
            System.out.println();
            System.out.println("------------------------------------");
            System.out.println();
            System.out.println();
        }
    }

    /**
     * 创建ZIP文件
     */
    @Test
    public void zip() {
        GlobalConfig globalConfigBuild = GlobalConfig.builder()
                .sourcePath("")
                .build();

        PackageConfig packageConfigBuilder = PackageConfig.builder()
                .packagePath("com.xh.example")
                .resourcePath("")
                .build();

        StrategyConfig strategyConfigBuilder = StrategyConfig.builder()
                .tableNames(Arrays.asList("gateway_route", "gateway_route_item"))
                .build();

        TemplateConfig templateConfigBuilder = TemplateConfig.builder()
                .selectedOutputFiles(Arrays.asList("entity", "mapper", "xml"))
                .build();

        ConfigInfo config = ConfigInfo.builder()
                .globalConfig(globalConfigBuild)
                .packageConfig(packageConfigBuilder)
                .strategyConfig(strategyConfigBuilder)
                .templateConfig(templateConfigBuilder)
                .build();

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream("D:/code/generator.zip"))) {
            Map<String, String> map = domain.outputString(config);
            map.forEach((k, v) -> {
                try {
                    zip.putNextEntry(new ZipEntry(k));
                    IoUtil.writeUtf8(zip, false, v);
                    zip.flush();
                    zip.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}