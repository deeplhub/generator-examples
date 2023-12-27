package com.xh.mbp.generator.factory.dto;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * @author H.Yang
 * @date 2023/12/25
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class GlobalConfigDTO {

    /**
     * 作者
     */
    private String author;

    /**
     * 禁止打开输出目录，默认不打开
     */
    private boolean disableOpenDir;

    /**
     * 源代码路径
     */
    private String sourcePath;

    public String getAuthor() {
        return StrUtil.isBlank(author) ? System.getProperty("user.name") : author;
    }

    public String getSourcePath() {
        return new File(this.sourcePath + "/src/main/java").toString();
    }
}
