package com.xh.generator.example.infrastructure.engine;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Spring模板引擎
 *
 * @author H.Yang
 * @date 2022/6/16
 */
public class StringTemplateEngine extends FreemarkerTemplateEngine {

    private Configuration configuration;
    private final Map<String, String> dataMap = new LinkedHashMap<>();

    public StringTemplateEngine() {
    }

    @Override
    public StringTemplateEngine init(ConfigBuilder configBuilder) {
        this.configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        this.configuration.setDefaultEncoding(ConstVal.UTF8);
        this.configuration.setClassForTemplateLoading(FreemarkerTemplateEngine.class, "/");
        return this;
    }

    @Override
    public AbstractTemplateEngine batchOutput() {
        try {
            ConfigBuilder config = this.getConfigBuilder();
            List<TableInfo> tableInfoList = config.getTableInfoList();
            tableInfoList.forEach((tableInfo) -> {
                Map<String, Object> objectMap = this.getObjectMap(config, tableInfo);
                Optional.ofNullable(config.getInjectionConfig()).ifPresent((t) -> {
                    t.beforeOutputFile(tableInfo, objectMap);
                    this.outputCustomFile(t.getCustomFile(), tableInfo, objectMap);
                });
                this.outputEntity(tableInfo, objectMap);
                this.outputMapper(tableInfo, objectMap);
                this.outputService(tableInfo, objectMap);
                this.outputController(tableInfo, objectMap);
            });
            return this;
        } catch (Exception var3) {
            throw new RuntimeException("无法创建文件，请检查配置信息！", var3);
        }
    }

    protected void outputCustomFile(Map<String, String> customFile, TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String otherPath = this.getPathInfo(OutputFile.other);
        customFile.forEach((key, value) -> {
            String fileName = String.format(otherPath + File.separator + entityName + File.separator + "%s", key);
            this.outputStr(objectMap, fileName, value);
        });
    }


    @Override
    protected void outputEntity(TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String entityPath = this.getPathInfo(OutputFile.entity);
        if (StringUtils.isNotBlank(entityName) && StringUtils.isNotBlank(entityPath)) {
            this.getTemplateFilePath((template) -> template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())).ifPresent((entity) -> {
                String entityFile = String.format(entityPath + File.separator + "%s" + this.suffixJavaOrKt(), entityName);
//                entityFile = entityName + this.suffixJavaOrKt();
                this.outputStr(objectMap, entityFile, entity);
            });
        }
    }

    @Override
    protected void outputMapper(TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String mapperPath = this.getPathInfo(OutputFile.mapper);
        if (StringUtils.isNotBlank(tableInfo.getMapperName()) && StringUtils.isNotBlank(mapperPath)) {
            this.getTemplateFilePath(TemplateConfig::getMapper).ifPresent((mapper) -> {
                String mapperFile = String.format(mapperPath + File.separator + tableInfo.getMapperName() + this.suffixJavaOrKt(), entityName);
//                mapperFile = tableInfo.getMapperName() + this.suffixJavaOrKt();
                this.outputStr(objectMap, mapperFile, mapper);
            });
        }

        String xmlPath = this.getPathInfo(OutputFile.xml);
        if (StringUtils.isNotBlank(tableInfo.getXmlName()) && StringUtils.isNotBlank(xmlPath)) {
            this.getTemplateFilePath(TemplateConfig::getXml).ifPresent((xml) -> {
                String xmlFile = String.format(xmlPath + File.separator + tableInfo.getXmlName() + ".xml", entityName);
//                xmlFile = tableInfo.getXmlName() + ".xml";
                this.outputStr(objectMap, xmlFile, xml);
            });
        }

    }

    @Override
    protected void outputService(TableInfo tableInfo, Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        String servicePath = this.getPathInfo(OutputFile.service);
        if (StringUtils.isNotBlank(tableInfo.getServiceName()) && StringUtils.isNotBlank(servicePath)) {
            this.getTemplateFilePath(TemplateConfig::getService).ifPresent((service) -> {
                String serviceFile = String.format(servicePath + File.separator + tableInfo.getServiceName() + this.suffixJavaOrKt(), entityName);
//                serviceFile = tableInfo.getServiceName() + this.suffixJavaOrKt();
                this.outputStr(objectMap, serviceFile, service);
            });
        }

        String serviceImplPath = this.getPathInfo(OutputFile.serviceImpl);
        if (StringUtils.isNotBlank(tableInfo.getServiceImplName()) && StringUtils.isNotBlank(serviceImplPath)) {
            this.getTemplateFilePath(TemplateConfig::getServiceImpl).ifPresent((serviceImpl) -> {
                String implFile = String.format(serviceImplPath + File.separator + tableInfo.getServiceImplName() + this.suffixJavaOrKt(), entityName);
//                implFile = tableInfo.getServiceImplName() + this.suffixJavaOrKt();
                this.outputStr(objectMap, implFile, serviceImpl);
            });
        }

    }

    @Override
    protected void outputController(TableInfo tableInfo, Map<String, Object> objectMap) {
        String controllerPath = this.getPathInfo(OutputFile.controller);
        if (StringUtils.isNotBlank(tableInfo.getControllerName()) && StringUtils.isNotBlank(controllerPath)) {
            this.getTemplateFilePath(TemplateConfig::getController).ifPresent((controller) -> {
                String entityName = tableInfo.getEntityName();
                String controllerFile = String.format(controllerPath + File.separator + tableInfo.getControllerName() + this.suffixJavaOrKt(), entityName);
//                controllerFile = tableInfo.getControllerName() + this.suffixJavaOrKt();
                this.outputStr(objectMap, controllerFile, controller);
            });
        }

    }

    @Override
    protected Optional<String> getTemplateFilePath(Function<TemplateConfig, String> function) {
        TemplateConfig templateConfig = this.getConfigBuilder().getTemplateConfig();
        String filePath = function.apply(templateConfig);
        String path = this.templateFilePath(filePath);
        return StringUtils.isNotBlank(filePath) ? Optional.of(path) : Optional.empty();
    }


    protected void outputStr(Map<String, Object> objectMap, String fileName, String templatePath) {
        try {
            Template template = this.configuration.getTemplate(templatePath);
            //写出到控制台
            StringWriter sw = new StringWriter();
            //进行填充 数据和信息。
            template.process(objectMap, sw);

            dataMap.put(fileName, sw.toString());
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public Map<String, String> getDataMap() {
        return dataMap;
    }
}
