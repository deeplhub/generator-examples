package com.xh.generator.example.dmoain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author H.Yang
 * @date 2023/12/26
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConfigInfo {
    private GlobalConfig globalConfig;
    private PackageConfig packageConfig;
    private StrategyConfig strategyConfig;
    private TemplateConfig templateConfig;

}
