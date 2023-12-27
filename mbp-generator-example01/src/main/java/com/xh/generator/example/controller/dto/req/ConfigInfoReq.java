package com.xh.generator.example.controller.dto.req;

import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author H.Yang
 * @date 2023/12/21
 */
@Data
public class ConfigInfoReq {

    /**
     * 表名称
     */
    @NotEmpty(message = "表名称不能为空")
    private List<String> tableNames = new ArrayList<>();

    /**
     * 作者
     */
    private String author;

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 表前缀
     */
    private List<String> tablePrefixs = new ArrayList<>();

    /**
     * 选择的输出文件
     */
    @NotEmpty(message = "未选择输出文件")
    private List<String> selectedOutputFiles = new ArrayList<>();

    /**
     * 启用表字段注释
     */
    private boolean enableTableFieldAnnotation;

    /**
     * 禁止打开输出目录，默认打开
     */
    private boolean disableOpenDir;

    /**
     * 文件类型：1-zip文件，2-java文件
     */
    @NotNull(message = "文件类型不能为空")
    @Range(min = 1, max = 2, message = "文件类型只能为1或2")
    private Integer fileType;

    /**
     * 目标项目根目录（文件类型为2时必选）
     */
    private String outputDir;

    public String getOutputDir() {
        if (fileType == 2 && StrUtil.isBlank(outputDir)) {
            throw new IllegalArgumentException("目标项目根目录不能为空");
        }
        return outputDir;
    }

}