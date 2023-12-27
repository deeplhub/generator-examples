package com.xh.mbp.generator.dto;

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
public class TemplateConfigDTO {

    /**
     * 选择的输出文件
     */
    private List<String> selectedOutputFiles;

}
