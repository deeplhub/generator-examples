package com.xh.generator.example.controller.assembler;

import com.xh.generator.example.controller.dto.resp.TableInfoResp;
import com.xh.generator.example.dmoain.model.TableInfo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author H.Yang
 * @date 2023/12/26
 */
public class TableInfoAssembler {

    public static List<TableInfoResp> toDTO(List<TableInfo> list) {
        return list.parallelStream().map(o -> toDTO(o)).collect(Collectors.toList());
    }

    private static TableInfoResp toDTO(TableInfo tableInfo) {
        TableInfoResp resp = new TableInfoResp();

        resp.setName(tableInfo.getName());
        resp.setComment(tableInfo.getComment());

        return resp;
    }

}
