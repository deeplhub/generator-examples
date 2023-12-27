package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
<#if mapperAnnotationClass??>
import ${mapperAnnotationClass.name};
</#if>

/**
 * ${table.comment!} Mapper 接口
 *
 * @author ${author}
 * @date ${date}
 */
<#if mapperAnnotationClass??>
@${mapperAnnotationClass.simpleName}
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {


    /**
     * 分页查询
     *
     * @param page
     * @param queryWrapper
     * @return
     */
    @Select("SQL语句")
    List<${entity}> pageList(@Param("page") Page<${entity}> page, @Param(Constants.WRAPPER) QueryWrapper<${entity}> queryWrapper);

}
</#if>
