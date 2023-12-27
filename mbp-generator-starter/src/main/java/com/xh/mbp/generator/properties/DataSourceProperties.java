package com.xh.mbp.generator.properties;

import lombok.Data;

/**
 * @author H.Yang
 * @date 2023/12/27
 */
@Data
public class DataSourceProperties {

    private String jdbcUrl;
    private String username;
    private String password;
}
