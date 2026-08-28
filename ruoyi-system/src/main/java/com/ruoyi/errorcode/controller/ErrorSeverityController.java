package com.ruoyi.errorcode.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.service.IErrorSeverityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 错误码严重程度Controller
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@RestController
@RequestMapping("/errorcode/severity")
@Tag(name = "错误码严重程度管理", description = "错误码严重程度的增删改查接口")
public class ErrorSeverityController extends BaseController
{
    @Autowired
    private IErrorSeverityService errorSeverityService;

    /**
     * 查询错误码严重程度列表
     */
    @PreAuthorize("@ss.hasPermi('errorcode:severity:list')")
    @Operation(summary = "查询错误码严重程度列表", parameters = {
        @Parameter(name = "pageNum", description = "当前页码", in = ParameterIn.QUERY, example = "1"),
        @Parameter(name = "pageSize", description = "每页数量", in = ParameterIn.QUERY, example = "10"),
        @Parameter(name = "orderByColumn", description = "排序字段", in = ParameterIn.QUERY),
        @Parameter(name = "isAsc", description = "排序方向：asc 或 desc", in = ParameterIn.QUERY, example = "asc"),
        @Parameter(name = "reasonable", description = "分页参数合理化", in = ParameterIn.QUERY, example = "true"),
        @Parameter(name = "params[beginCreateTime]", description = "创建时间开始", in = ParameterIn.QUERY),
        @Parameter(name = "params[endCreateTime]", description = "创建时间结束", in = ParameterIn.QUERY),
        @Parameter(name = "params[beginUpdateTime]", description = "更新时间开始", in = ParameterIn.QUERY),
        @Parameter(name = "params[endUpdateTime]", description = "更新时间结束", in = ParameterIn.QUERY)
    })
    @GetMapping("/list")
    public TableDataInfo list(@ParameterObject ErrorSeverity errorSeverity)
    {
        startPage();
        List<ErrorSeverity> list = errorSeverityService.selectErrorSeverityList(errorSeverity);
        return getDataTable(list);
    }

    /**
     * 导出错误码严重程度列表
     */
    @PreAuthorize("@ss.hasPermi('errorcode:severity:export')")
    @Log(title = "错误码严重程度", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出错误码严重程度列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @ParameterObject ErrorSeverity errorSeverity)
    {
        List<ErrorSeverity> list = errorSeverityService.selectErrorSeverityList(errorSeverity);
        ExcelUtil<ErrorSeverity> util = new ExcelUtil<ErrorSeverity>(ErrorSeverity.class);
        util.exportExcel(response, list, "错误码严重程度数据");
    }

    /**
     * 获取错误码严重程度详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorcode:severity:query')")
    @Operation(summary = "获取错误码严重程度详情")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "严重程度ID", required = true)
                              @PathVariable("id") Long id)
    {
        return success(errorSeverityService.selectErrorSeverityById(id));
    }

    /**
     * 新增错误码严重程度
     */
    @PreAuthorize("@ss.hasPermi('errorcode:severity:add')")
    @Log(title = "错误码严重程度", businessType = BusinessType.INSERT)
    @Operation(summary = "新增错误码严重程度")
    @PostMapping
    public AjaxResult add(@RequestBody ErrorSeverity errorSeverity)
    {
        errorSeverity.setCreateBy(getUsername());
        return toAjax(errorSeverityService.insertErrorSeverity(errorSeverity));
    }

    /**
     * 修改错误码严重程度
     */
    @PreAuthorize("@ss.hasPermi('errorcode:severity:edit')")
    @Log(title = "错误码严重程度", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改错误码严重程度")
    @PutMapping
    public AjaxResult edit(@RequestBody ErrorSeverity errorSeverity)
    {
        errorSeverity.setUpdateBy(getUsername());
        return toAjax(errorSeverityService.updateErrorSeverity(errorSeverity));
    }

    /**
     * 删除错误码严重程度
     */
    @PreAuthorize("@ss.hasPermi('errorcode:severity:remove')")
    @Log(title = "错误码严重程度", businessType = BusinessType.DELETE)
    @Operation(summary = "删除错误码严重程度", description = "支持批量删除，多个严重程度ID使用逗号分隔")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "严重程度ID，多个使用逗号分隔", required = true)
                             @PathVariable Long[] ids)
    {
        return toAjax(errorSeverityService.deleteErrorSeverityByIds(ids));
    }
}
