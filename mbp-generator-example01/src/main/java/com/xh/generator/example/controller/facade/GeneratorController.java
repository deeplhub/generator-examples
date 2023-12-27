package com.xh.generator.example.controller.facade;

import cn.hutool.core.io.IoUtil;
import com.xh.generator.example.application.MbpGeneratorApplication;
import com.xh.generator.example.controller.assembler.ConfigInfoAssembler;
import com.xh.generator.example.controller.assembler.TableInfoAssembler;
import com.xh.generator.example.controller.dto.req.ConfigInfoReq;
import com.xh.generator.example.controller.dto.resp.TableInfoResp;
import com.xh.generator.example.dmoain.model.TableInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author H.Yang
 * @date 2023/12/21
 */
@Slf4j
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    @Resource
    private MbpGeneratorApplication application;

    /**
     * 获取当前数据库下所有表信息
     *
     * @return
     */
    @GetMapping(value = "/listTableInfo")
    List<TableInfoResp> listTableInfo() {
        List<TableInfo> list = application.listTableInfo();
        return TableInfoAssembler.toDTO(list);
    }

    /**
     * 生成类
     *
     * @param request
     */
    @PostMapping(value = "/create")
    void create(@RequestBody @Valid ConfigInfoReq request) {
        application.create(ConfigInfoAssembler.toDO(request));
    }

    /**
     * 预览
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/preview")
    Map<String, String> preview(@RequestBody @Valid ConfigInfoReq request) {

        return application.preview(ConfigInfoAssembler.toDO(request));
    }

    /**
     * 下载
     *
     * @param response
     * @param request
     */
    @PostMapping(value = "/download")
    void download(HttpServletResponse response, @RequestBody ConfigInfoReq request) {
        byte[] bytes = application.getByteArray(ConfigInfoAssembler.toDO(request));

        response.reset();
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=mbp-code-generation.zip");
        response.addHeader("Content-Length", "" + bytes.length);

        try (ServletOutputStream out = response.getOutputStream()) {
            IoUtil.write(out, true, bytes);
        } catch (IOException e) {
            log.error("文件下载失败", e);
        }
    }


}
