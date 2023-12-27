package com.xh.generator.example.application;

import cn.hutool.core.io.IoUtil;
import com.xh.generator.example.dmoain.model.ConfigInfo;
import com.xh.generator.example.dmoain.model.TableInfo;
import com.xh.generator.example.dmoain.service.DataBaseDomain;
import com.xh.generator.example.dmoain.service.MbpGeneratorDomain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author H.Yang
 * @date 2023/12/26
 */
@Slf4j
@Component
public class MbpGeneratorApplication {

    @Resource
    private DataBaseDomain dataBaseDomain;
    @Resource
    private MbpGeneratorDomain generatorDomain;

    public List<TableInfo> listTableInfo() {

        return dataBaseDomain.listTableInfo();
    }

    public void create(ConfigInfo config) {

        generatorDomain.create(config);
    }

    public Map<String, String> preview(ConfigInfo config) {

        return generatorDomain.outputString(config);
    }

    public byte[] getByteArray(ConfigInfo config) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(byteArrayOutputStream);

        Map<String, String> map = generatorDomain.outputString(config);
        map.forEach((k, v) -> {
            try {
                zip.putNextEntry(new ZipEntry(k));

                IoUtil.writeUtf8(zip, false, v);

                // 刷新缓冲区，一般写字符时用到,因为写字符时先进入缓冲区,然后将内存中的数据立刻写出(因为缓冲区是写满之后才会写出, 用flush()就不必等到缓冲区满,立刻写出)流对象还可以继续使用.
                zip.flush();
                // 关闭当前 ZIP 条目并定位流以写入下一个条目
                zip.closeEntry();
            } catch (IOException e) {
                log.error("渲染模板失败", e);
            }
        });

        return byteArrayOutputStream.toByteArray();
    }


}
