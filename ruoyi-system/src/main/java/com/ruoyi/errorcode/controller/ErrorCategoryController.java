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
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.service.IErrorCategoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 错误码类别Controller
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@RestController
@RequestMapping("/errorcode/category")
@Tag(name = "错误码类别管理", description = "错误码类别的增删改查接口")
public class ErrorCategoryController extends BaseController
{
    @Autowired
    private IErrorCategoryService errorCategoryService;

    /**
     * 查询错误码类别列表
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:list')")
    @Operation(summary = "查询错误码类别列表", parameters = {
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
    public TableDataInfo list(@ParameterObject ErrorCategory errorCategory)
    {
        startPage();
        List<ErrorCategory> list = errorCategoryService.selectErrorCategoryList(errorCategory);
        return getDataTable(list);
    }

    /**
     * 导出错误码类别列表
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:export')")
    @Log(title = "错误码类别", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出错误码类别列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @ParameterObject ErrorCategory errorCategory)
    {
        List<ErrorCategory> list = errorCategoryService.selectErrorCategoryList(errorCategory);
        ExcelUtil<ErrorCategory> util = new ExcelUtil<ErrorCategory>(ErrorCategory.class);
        util.exportExcel(response, list, "错误码类别数据");
    }

    /**
     * 获取错误码类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:query')")
    @Operation(summary = "获取错误码类别详情")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "类别ID", required = true)
                              @PathVariable("id") Long id)
    {
        return success(errorCategoryService.selectErrorCategoryById(id));
    }

    /**
     * 新增错误码类别
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:add')")
    @Log(title = "错误码类别", businessType = BusinessType.INSERT)
    @Operation(summary = "新增错误码类别")
    @PostMapping
    public AjaxResult add(@RequestBody ErrorCategory errorCategory)
    {
        errorCategory.setCreateBy(getUsername());
        return toAjax(errorCategoryService.insertErrorCategory(errorCategory));
    }

    /**
     * 修改错误码类别
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:edit')")
    @Log(title = "错误码类别", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改错误码类别")
    @PutMapping
    public AjaxResult edit(@RequestBody ErrorCategory errorCategory)
    {
        errorCategory.setUpdateBy(getUsername());
        return toAjax(errorCategoryService.updateErrorCategory(errorCategory));
    }

    /**
     * 删除错误码类别
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:remove')")
    @Log(title = "错误码类别", businessType = BusinessType.DELETE)
    @Operation(summary = "删除错误码类别", description = "支持批量删除，多个类别ID使用逗号分隔")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "类别ID，多个使用逗号分隔", required = true)
                             @PathVariable Long[] ids)
    {
        return toAjax(errorCategoryService.deleteErrorCategoryByIds(ids));
    }
}
