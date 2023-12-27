package com.xh.mbp.generator.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.xh.mbp.generator.factory.dto.GlobalConfigDTO;
import com.xh.mbp.generator.factory.dto.PackageConfigDTO;
import com.xh.mbp.generator.factory.dto.StrategyConfigDTO;
import com.xh.mbp.generator.factory.dto.TemplateConfigDTO;
import com.xh.mbp.generator.model.ConfigInfo;
import com.xh.mbp.generator.properties.DataSourceProperties;
import com.xh.mbp.generator.properties.MbpGeneratorProperties;
import com.xh.mbp.generator.template.MbpGeneratorTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Slf4j
@Configuration
public class MbpGeneratorConfiguration {

    @Bean
    @ConditionalOnProperty(value = "mybatis-plus.generator.enabled", havingValue = "true", matchIfMissing = true)
    public MbpGeneratorTemplate templatePropertie(MbpGeneratorProperties generatorProperties) {
        MbpGeneratorTemplate template = null;
        if (generatorProperties.isEnabled()) {
            DataSourceProperties dataSource = generatorProperties.getDataSource();

            template = new MbpGeneratorTemplate(dataSource.getJdbcUrl(), dataSource.getUsername(), dataSource.getPassword());
            template.setConfigInfo(this.getConfigInfo(generatorProperties));
        }
        return template;
    }

    @Bean
    @ConditionalOnProperty(value = "mybatis-plus.generator.enabled", havingValue = "false", matchIfMissing = true)
    public MbpGeneratorTemplate templateDataSource(DataSource dataSource) {
        MbpGeneratorTemplate template = null;

        if (dataSource instanceof DruidDataSource) {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;

            template = new MbpGeneratorTemplate(druidDataSource.getUrl(), druidDataSource.getUsername(), druidDataSource.getPassword());
        } else {
            throw new RuntimeException("Unknown DataSource Type");
        }
        return template;
    }


    private ConfigInfo getConfigInfo(MbpGeneratorProperties generatorProperties) {
        GlobalConfigDTO globalConfig = GlobalConfigDTO.builder()
                .author(generatorProperties.getAuthor())
                .disableOpenDir(generatorProperties.isDisableOpenDir())
                .sourcePath(generatorProperties.getOutputDir())
                .build();

        PackageConfigDTO packageConfig = PackageConfigDTO.builder()
                .packagePath(generatorProperties.getPackagePath())
                .resourcePath(generatorProperties.getOutputDir())
                .build();

        StrategyConfigDTO strategyConfig = StrategyConfigDTO.builder()
                //.tableNames(tableNames)
                //.tablePrefixs(tablePrefixs)
                .enableTableFieldAnnotation(generatorProperties.isEnableTableFieldAnnotation())
                .build();

        TemplateConfigDTO templateConfig = TemplateConfigDTO.builder()
                .selectedOutputFiles(generatorProperties.getSelectedOutputFiles())
                .build();

        return ConfigInfo.builder()
                .globalConfig(globalConfig)
                .packageConfig(packageConfig)
                .strategyConfig(strategyConfig)
                .templateConfig(templateConfig)
                .build();
    }
}
