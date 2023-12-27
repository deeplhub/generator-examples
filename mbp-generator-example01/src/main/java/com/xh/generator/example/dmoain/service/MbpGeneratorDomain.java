package com.xh.generator.example.dmoain.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xh.generator.example.dmoain.model.ConfigInfo;
import com.xh.generator.example.dmoain.model.factory.MbpConfigFactory;
import com.xh.generator.example.infrastructure.engine.StringTemplateEngine;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * https://baomidou.com/pages/981406/#%E6%95%B0%E6%8D%AE%E5%BA%93%E9%85%8D%E7%BD%AE-datasourceconfig
 *
 * @author H.Yang
 * @date 2023/12/21
 */
@Service
public class MbpGeneratorDomain {

    @Resource
    private DruidDataSource druidDataSource;

    /**
     * 创建文件
     *
     * @param config
     */
    public void create(ConfigInfo config) {
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();

        FastAutoGenerator.create(druidDataSource.getUrl(), druidDataSource.getUsername(), druidDataSource.getPassword())
                .globalConfig(builder -> factory.getGlobalConfig(builder, config.getGlobalConfig()))
                .packageConfig(builder -> factory.getPackageConfig(builder, config.getPackageConfig()))
                .strategyConfig(builder -> factory.getStrategyConfig(builder, config.getStrategyConfig()))
                .templateConfig(builder -> factory.getTemplateConfig(builder, config.getTemplateConfig()))
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 输出内容
     *
     * @param config
     * @return
     */
    public Map<String, String> outputString(ConfigInfo config) {
        StringTemplateEngine stringTemplateEngine = new StringTemplateEngine();

        MbpConfigFactory factory = MbpConfigFactory.getSingleton();

        FastAutoGenerator.create(druidDataSource.getUrl(), druidDataSource.getUsername(), druidDataSource.getPassword())
                .globalConfig(builder -> factory.getGlobalConfig(builder, config.getGlobalConfig()))
                .packageConfig(builder -> factory.getPackageConfig(builder, config.getPackageConfig()))
                .strategyConfig(builder -> factory.getStrategyConfig(builder, config.getStrategyConfig()))
                .templateConfig(builder -> factory.getTemplateConfig(builder, config.getTemplateConfig()))
                .templateEngine(stringTemplateEngine)
                .execute();

        return stringTemplateEngine.getDataMap();
    }
}
