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
public class TemplateConfig {

    /**
     * 选择的输出文件
     */
    private List<String> selectedOutputFiles;

}
