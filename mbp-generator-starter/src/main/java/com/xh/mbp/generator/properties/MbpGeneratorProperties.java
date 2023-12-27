package com.xh.mbp.generator.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis-plus 配置
 *
 * <p>@ConfigurationProperties和@Configuration区别：<p/>
 * <p>
 * - @ConfigurationProperties 将yaml或者xml中的配置文件放到类中
 * - @Configuration或@Component 都是使 Spring @ComponentScan 能够扫到该类，把当前类放到 Spring 容器中，可以做为Bean使用
 *
 * <p>@Configuration和@Component区别</p>
 * - @Component 注解可以当做配置类，但是并不会为其生成CGLIB代理Class；当使用 @Configuration注解时，生成当前对象的子类Class
 * <p>
 * 参考地址：
 * https://blog.csdn.net/long476964/article/details/80626930
 *
 * @author H.Yang
 * @date 2021/7/31
 */
@Data
@ConfigurationProperties(prefix = "mybatis-plus.generator")
public class MbpGeneratorProperties {

    private boolean enabled = false;

    /**
     * 作者
     */
    private String author = System.getProperty("user.name");

    /**
     * 是否打开生成后的文件路径
     */
    private boolean disableOpenDir = true;

    /**
     * 导出路径
     */
    private String outputDir;

    /**
     * 包路径
     */
    private String packagePath = "com.xh.generator.example";

    /**
     * lombok
     */
    private boolean enableLombok = true;

    /**
     * 字段注解
     */
    private boolean enableTableFieldAnnotation = false;

    /**
     * 选定的输出文件
     */
    private List<String> selectedOutputFiles = new ArrayList<>();

    private DataSourceProperties dataSource;

    public String getSourcePath() {
        return new File(this.outputDir + "/src/main/java").toString();
    }

    public String getResourcePath() {
        return new File(this.outputDir + "/src/main/resources").toString();
    }
}
