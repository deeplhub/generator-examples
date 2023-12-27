package com.xh.mbp.generator.template;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.xh.mbp.generator.engine.StringTemplateEngine;
import com.xh.mbp.generator.factory.MbpConfigFactory;
import com.xh.mbp.generator.factory.dto.StrategyConfigDTO;
import com.xh.mbp.generator.model.ConfigInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Slf4j
public class MbpGeneratorTemplate {
    private ConfigInfo configInfo;
    private String jdbcUrl;
    private String username;
    private String password;

    public MbpGeneratorTemplate(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }

    /**
     * 创建文件
     * <p>
     * 读取YAML文件配置
     *
     * @param tableNames
     * @param tablePrefixs
     */
    public void create(List<String> tableNames, List<String> tablePrefixs) {
        if (configInfo == null) {
            throw new NullPointerException("配置信息不能为空");
        }

        StrategyConfigDTO strategyConfig = configInfo.getStrategyConfig();

        strategyConfig.setTableNames(tableNames);
        strategyConfig.setTablePrefixs(tablePrefixs);

        configInfo.setStrategyConfig(strategyConfig);
        this.generator(configInfo);
    }

    /**
     * 创建文件
     * <p>
     * 根据请求参数生成
     *
     * @param config
     */
    public void create(ConfigInfo config) {

        this.generator(config);
    }


    private void generator(ConfigInfo config) {
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();

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
     * <p>
     * 读取YAML文件配置
     *
     * @param tableNames
     * @param tablePrefixs
     * @return
     */
    public Map<String, String> outputString(List<String> tableNames, List<String> tablePrefixs) {
        if (configInfo == null) {
            throw new NullPointerException("配置信息不能为空");
        }

        StrategyConfigDTO strategyConfig = configInfo.getStrategyConfig();

        strategyConfig.setTableNames(tableNames);
        strategyConfig.setTablePrefixs(tablePrefixs);

        configInfo.setStrategyConfig(strategyConfig);


        return this.generatorString(configInfo);
    }


    /**
     * 输出内容
     *
     * @param config
     * @return
     */
    public Map<String, String> outputString(ConfigInfo config) {
        return this.generatorString(config);
    }

    private Map<String, String> generatorString(ConfigInfo config) {
        StringTemplateEngine stringTemplateEngine = new StringTemplateEngine();
        MbpConfigFactory factory = MbpConfigFactory.getSingleton();

        FastAutoGenerator.create(this.jdbcUrl, this.username, this.password)
                .globalConfig(builder -> factory.getGlobalConfig(builder, config.getGlobalConfig()))
                .packageConfig(builder -> factory.getPackageConfig(builder, config.getPackageConfig()))
                .strategyConfig(builder -> factory.getStrategyConfig(builder, config.getStrategyConfig()))
                .templateConfig(builder -> factory.getTemplateConfig(builder, config.getTemplateConfig()))
                .templateEngine(stringTemplateEngine)
                .execute();

        return stringTemplateEngine.getDataMap();
    }

    public void setConfigInfo(ConfigInfo configInfo) {
        this.configInfo = configInfo;
    }
}
