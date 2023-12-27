package com.xh.mbp.generator.factory;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.Controller;
import com.baomidou.mybatisplus.generator.config.builder.Entity;
import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.baomidou.mybatisplus.generator.config.builder.Service;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.xh.mbp.generator.factory.dto.GlobalConfigDTO;
import com.xh.mbp.generator.factory.dto.PackageConfigDTO;
import com.xh.mbp.generator.factory.dto.StrategyConfigDTO;
import com.xh.mbp.generator.factory.dto.TemplateConfigDTO;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author H.Yang
 * @date 2023/12/25
 */
public class MbpConfigFactory {

    private static class Inner {
        private static final MbpConfigFactory instance = new MbpConfigFactory();
    }

    private MbpConfigFactory() {
    }

    public static MbpConfigFactory getSingleton() {
        return Inner.instance;
    }


    /**
     * 全局配置
     *
     * @param builder
     * @param dto
     */
    public void getGlobalConfig(GlobalConfig.Builder builder, GlobalConfigDTO dto) {
        // 设置作者
        builder.author(dto.getAuthor())
                // 日期类型的字段使用哪个类型，默认是 java8的 日期类型，此处改为 java.util.date
                .dateType(DateType.ONLY_DATE)
                // 指定输出目录
                .outputDir(dto.getSourcePath());

        // 禁止打开输出目录，默认打开
        if (!dto.isDisableOpenDir()) {
            builder.disableOpenDir();
        }
    }

    /**
     * 包配置
     *
     * @param builder
     * @param dto
     */
    public void getPackageConfig(PackageConfig.Builder builder, PackageConfigDTO dto) {
        // 设置父包名
        builder.parent(dto.getPackagePath())
                //Mapper XML 包名
                .xml("mapper")
                // 设置mapperXml生成路径
                .pathInfo(Collections.singletonMap(OutputFile.xml, dto.getResourcePath()));
    }

    /**
     * @param builder
     * @param dto
     */
    public void getTemplateConfig(TemplateConfig.Builder builder, TemplateConfigDTO dto) {
        builder.entity("templates/java/entity.java");
        builder.mapper("templates/java/mapper.java");
        builder.xml("templates/java/mapper.xml");
        builder.service("templates/java/service.java");
        builder.serviceImpl("templates/java/serviceImpl.java");
        builder.controller("templates/java/controller.java");

        Map<TemplateType, String> fileSuffixDict = new HashMap<>();

        fileSuffixDict.put(TemplateType.ENTITY, "entity");
        fileSuffixDict.put(TemplateType.MAPPER, "mapper");
        fileSuffixDict.put(TemplateType.XML, "xml");
        fileSuffixDict.put(TemplateType.SERVICE, "service");
        fileSuffixDict.put(TemplateType.SERVICE_IMPL, "service");
        fileSuffixDict.put(TemplateType.CONTROLLER, "controller");

        fileSuffixDict.entrySet().stream().filter(entry -> !dto.getSelectedOutputFiles().contains(entry.getValue())).forEach(entry -> builder.disable(entry.getKey()));
    }

    /**
     * 策略配置
     *
     * @param builder
     * @param dto
     */
    public void getStrategyConfig(StrategyConfig.Builder builder, StrategyConfigDTO dto) {
        builder.addInclude(dto.getTableNames()); // 设置需要生成的表名
        // 设置过滤表前缀
        if (dto.getTablePrefixs() != null && !dto.getTablePrefixs().isEmpty()) {
            builder.addTablePrefix(dto.getTablePrefixs());
        }

        this.entityConfig(builder.entityBuilder(), dto);
        this.mapperConfig(builder.mapperBuilder());
        this.serviceConfig(builder.serviceBuilder());
        this.controllerConfig(builder.controllerBuilder());
    }

    private void entityConfig(Entity.Builder entityBuilder, StrategyConfigDTO dto) {
        // 实体类策略配置
        entityBuilder.enableFileOverride()
                .disableSerialVersionUID()
                //逻辑删除字段名。逻辑删除字段名可以通过全局配置设置
                //.logicDeleteColumnName("deleted")
                //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                .addTableFills(new Column("create_at", FieldFill.INSERT))
                .addTableFills(new Column("update_at", FieldFill.INSERT_UPDATE))
                .formatFileName("%sEntity");

        if (dto.isEnableLombok()) {
            entityBuilder.enableLombok();
        }

        // 开启生成实体时生成字段注解。会在实体类的属性前，添加[@TableField("nickname")]
        if (dto.isEnableTableFieldAnnotation()) {
            entityBuilder.enableTableFieldAnnotation();
        }
    }

    private void mapperConfig(Mapper.Builder mapperBuilder) {
        mapperBuilder.superClass(ConstVal.SUPER_MAPPER_CLASS)
                .enableFileOverride()
                // 开启 @Mapper 注解,会在mapper接口上添加注解[@Mapper]
                .enableMapperAnnotation()
                // 启用 BaseResultMap 生成。会在mapper.xml文件生成[通用查询映射结果]配置。
                .enableBaseResultMap()
                // 启用 BaseColumnList。会在mapper.xml文件生成[通用查询结果列 ]配置
                .enableBaseColumnList()
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sMapper");

    }

    private void serviceConfig(Service.Builder serviceBuilder) {
        serviceBuilder.superServiceClass(ConstVal.SUPER_SERVICE_CLASS)
                .enableFileOverride()
                .superServiceImplClass(ConstVal.SUPER_SERVICE_IMPL_CLASS) // service实现类的包
                //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                .formatServiceFileName("%sService")
                //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl
                .formatServiceImplFileName("%sServiceImpl");
    }

    private void controllerConfig(Controller.Builder controllerBuilder) {
        //Controller策略配置
        controllerBuilder.enableRestStyle() // 开启驼峰转连字符
                .enableFileOverride()
                //格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
                .formatFileName("%sController")
                .enableHyphenStyle(); // 开启生成@RestController 控制器
    }
}
