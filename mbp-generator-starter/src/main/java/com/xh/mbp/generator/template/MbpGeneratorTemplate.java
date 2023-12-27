package com.xh.mbp.generator.template;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xh.mbp.generator.engine.StringTemplateEngine;
import com.xh.mbp.generator.factory.MbpConfigFactory;
import com.xh.mbp.generator.factory.dto.GlobalConfigDTO;
import com.xh.mbp.generator.factory.dto.PackageConfigDTO;
import com.xh.mbp.generator.factory.dto.StrategyConfigDTO;
import com.xh.mbp.generator.factory.dto.TemplateConfigDTO;
import com.xh.mbp.generator.model.ConfigInfo;
import com.xh.mbp.generator.properties.DataSourceProperties;
import com.xh.mbp.generator.properties.MbpGeneratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Slf4j
@EnableConfigurationProperties(MbpGeneratorProperties.class)
public class MbpGeneratorTemplate {

    private MbpGeneratorProperties generatorProperties;
    private String jdbcUrl;
    private String username;
    private String password;


    public MbpGeneratorTemplate(MbpGeneratorProperties generatorProperties, ApplicationContext applicationContext) {
        this.generatorProperties = generatorProperties;
        if (generatorProperties.isEnabled()) {
            DataSourceProperties dataSource = generatorProperties.getDataSource();

            this.jdbcUrl = dataSource.getJdbcUrl();
            this.username = dataSource.getUsername();
            this.password = dataSource.getPassword();
            return;
        }

        DataSource dataSource = applicationContext.getBean(DataSource.class);
        if (dataSource instanceof DruidDataSource) {
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;

            this.jdbcUrl = druidDataSource.getUrl();
            this.username = druidDataSource.getUsername();
            this.password = druidDataSource.getPassword();
        } else {
            throw new RuntimeException("Unknown DataSource Type");
        }
    }

    /**
     * 创建文件
     */
    public void create(List<String> tableNames, List<String> tablePrefixs) {
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();
        ConfigInfo config = this.getConfigInfo(tableNames, tablePrefixs);
        this.generator(config, factory);
    }

    /**
     * 创建文件
     *
     * @param config
     */
    public void create(ConfigInfo config) {
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();
        this.generator(config, factory);
    }


    private void generator(ConfigInfo config, MbpConfigFactory factory) {
        FastAutoGenerator.create(this.jdbcUrl, this.username, this.password)
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
     * @return
     */
    public Map<String, String> outputString(List<String> tableNames, List<String> tablePrefixs) {
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();
        ConfigInfo config = this.getConfigInfo(tableNames, tablePrefixs);

        return this.generatorString(factory, config);
    }


    /**
     * 输出内容
     *
     * @param config
     * @return
     */
    public Map<String, String> outputString(ConfigInfo config) {
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();

        return this.generatorString(factory, config);
    }

    private Map<String, String> generatorString(MbpConfigFactory factory, ConfigInfo config) {
        StringTemplateEngine stringTemplateEngine = new StringTemplateEngine();

        FastAutoGenerator.create(this.jdbcUrl, this.username, this.password)
                .globalConfig(builder -> factory.getGlobalConfig(builder, config.getGlobalConfig()))
                .packageConfig(builder -> factory.getPackageConfig(builder, config.getPackageConfig()))
                .strategyConfig(builder -> factory.getStrategyConfig(builder, config.getStrategyConfig()))
                .templateConfig(builder -> factory.getTemplateConfig(builder, config.getTemplateConfig()))
                .templateEngine(stringTemplateEngine)
                .execute();

        return stringTemplateEngine.getDataMap();
    }

    private ConfigInfo getConfigInfo(List<String> tableNames, List<String> tablePrefixs) {
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
                .tableNames(tableNames)
                .tablePrefixs(tablePrefixs)
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
