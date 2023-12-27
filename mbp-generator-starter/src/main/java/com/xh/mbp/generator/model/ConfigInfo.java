package com.xh.mbp.generator.model;

import com.xh.mbp.generator.factory.dto.GlobalConfigDTO;
import com.xh.mbp.generator.factory.dto.PackageConfigDTO;
import com.xh.mbp.generator.factory.dto.StrategyConfigDTO;
import com.xh.mbp.generator.factory.dto.TemplateConfigDTO;
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
    private GlobalConfigDTO globalConfig;
    private PackageConfigDTO packageConfig;
    private StrategyConfigDTO strategyConfig;
    private TemplateConfigDTO templateConfig;

}
