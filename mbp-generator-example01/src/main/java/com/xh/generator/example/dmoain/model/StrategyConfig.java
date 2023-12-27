package com.xh.generator.example.dmoain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author H.Yang
 * @date 2023/12/25
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StrategyConfig {

    /**
     * 表名称
     */
    private List<String> tableNames;

    /**
     * 表前缀
     */
    private List<String> tablePrefixs;

    /**
     * 启动Lombok，默认启用
     */
    private boolean enableLombok = true;

    /**
     * 启用表字段注释
     */
    private boolean enableTableFieldAnnotation;

}
