package com.xh.mbp.generator.dto;

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
public class PackageConfigDTO {

    /**
     * 包路径
     */
    private String packagePath;

    /**
     * 资源路径
     */
    private String resourcePath;

    public String getPackagePath() {
        return StrUtil.isBlank(packagePath) ? "com.xh.example" : packagePath;
    }

    public String getResourcePath() {
        return new File(this.resourcePath + "/src/main/resources").toString();
    }
}
