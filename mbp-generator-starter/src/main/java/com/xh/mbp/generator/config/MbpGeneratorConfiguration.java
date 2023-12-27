package com.xh.mbp.generator.config;

import com.xh.mbp.generator.properties.MbpGeneratorProperties;
import com.xh.mbp.generator.template.MbpGeneratorTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Slf4j
@Configuration
public class MbpGeneratorConfiguration {

    @Bean
    @ConditionalOnProperty(value = "mybatis-plus.generator.enabled", havingValue = "true", matchIfMissing = false)
    public MbpGeneratorTemplate generatorTemplate(MbpGeneratorProperties generatorProperties) {

        return null;
    }
}
