package com.xh.generator.example.controller.assembler;

import cn.hutool.core.io.FileUtil;
import com.xh.generator.example.controller.dto.req.ConfigInfoReq;
import com.xh.generator.example.dmoain.model.*;

import java.io.File;

/**
 * @author H.Yang
 * @date 2023/12/26
 */
public class ConfigInfoAssembler {

    public static ConfigInfo toDO(ConfigInfoReq request) {
        checkConfig(request);

        GlobalConfig globalConfigBuild = GlobalConfig.builder()
                .author(request.getAuthor())
                .disableOpenDir(request.isDisableOpenDir())
                .sourcePath(request.getOutputDir())
                .build();

        PackageConfig packageConfigBuilder = PackageConfig.builder()
                .packagePath(request.getPackagePath())
                .resourcePath(request.getOutputDir())
                .build();

        StrategyConfig strategyConfigBuilder = StrategyConfig.builder()
                .tableNames(request.getTableNames())
                .tablePrefixs(request.getTablePrefixs())
                .enableTableFieldAnnotation(request.isEnableTableFieldAnnotation())
                .build();

        TemplateConfig templateConfigBuilder = TemplateConfig.builder()
                .selectedOutputFiles(request.getSelectedOutputFiles())
                .build();

        ConfigInfo config = ConfigInfo.builder()
                .globalConfig(globalConfigBuild)
                .packageConfig(packageConfigBuilder)
                .strategyConfig(strategyConfigBuilder)
                .templateConfig(templateConfigBuilder)
                .build();

        return config;
    }

    /**
     * 检查配置
     *
     * @param request
     */
    private static void checkConfig(ConfigInfoReq request) {
        if (!FileUtil.isDirectory(request.getOutputDir())) {
            throw new RuntimeException("目标项目根目录错误，请确认目录有效且存在：" + request.getOutputDir());
        }

        if (!request.getOutputDir().endsWith(File.separator)) {
            request.setOutputDir(request.getOutputDir() + File.separator);
        }
    }


}
