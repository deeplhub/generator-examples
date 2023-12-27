package com.xh.mbp.generator.template;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xh.mbp.generator.engine.StringTemplateEngine;
import com.xh.mbp.generator.factory.MbpConfigFactory;
import com.xh.mbp.generator.properties.DataSourceProperties;
import com.xh.mbp.generator.properties.MbpGeneratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Slf4j
@EnableConfigurationProperties(MbpGeneratorProperties.class)
public class MbpGeneratorTemplate {

    @Resource
    private MbpGeneratorProperties generatorProperties;

    /**
     * 创建文件
     */
    public void create(List<String> tableNames, List<String> tablePrefixs) {
        DataSourceProperties dataSource = generatorProperties.getDataSource();
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();

        FastAutoGenerator.create(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword())
                .globalConfig(builder -> factory.getGlobalConfig(builder, generatorProperties))
                .packageConfig(builder -> factory.getPackageConfig(builder, generatorProperties))
                .strategyConfig(builder -> factory.getStrategyConfig(builder, tableNames, tablePrefixs, generatorProperties))
                .templateConfig(builder -> factory.getTemplateConfig(builder, generatorProperties))
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

    /**
     * 输出内容
     *
     * @return
     */
    public Map<String, String> outputString(List<String> tableNames, List<String> tablePrefixs) {
        StringTemplateEngine stringTemplateEngine = new StringTemplateEngine();

        DataSourceProperties dataSource = generatorProperties.getDataSource();
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();

        FastAutoGenerator.create(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword())
                .globalConfig(builder -> factory.getGlobalConfig(builder, generatorProperties))
                .packageConfig(builder -> factory.getPackageConfig(builder, generatorProperties))
                .strategyConfig(builder -> factory.getStrategyConfig(builder, tableNames, tablePrefixs, generatorProperties))
                .templateConfig(builder -> factory.getTemplateConfig(builder, generatorProperties))
                .templateEngine(stringTemplateEngine)
                .execute();

        return stringTemplateEngine.getDataMap();
    }

}
