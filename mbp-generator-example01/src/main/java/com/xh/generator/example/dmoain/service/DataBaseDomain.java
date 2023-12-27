package com.xh.generator.example.dmoain.service;

import com.xh.generator.example.dmoain.model.TableInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author H.Yang
 * @date 2023/12/21
 */
@Service
public class DataBaseDomain {

    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<TableInfo> listTableInfo() {
        String sql = "show table status WHERE 1=1 ";

        // query 查出本是 Map 类型，由 BeanPropertyRowMapper 转实体类
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper(TableInfo.class));

    }


}
