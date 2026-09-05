package com.ruoyi.errorcode.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
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
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.domain.response.ErrorCodeOpenApiResponses.ErrorCategoryDetailResponse;
import com.ruoyi.errorcode.domain.response.ErrorCodeOpenApiResponses.ErrorCategoryPageResponse;
import com.ruoyi.errorcode.domain.response.ErrorCodeOpenApiResponses.OperationResponse;
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
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCategoryPageResponse.class)))
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
    @ApiResponse(responseCode = "200", description = "Excel 文件",
            headers = @Header(name = HttpHeaders.CONTENT_DISPOSITION, description = "下载文件名",
                    schema = @Schema(type = "string", example = "attachment; filename=\"error-categories.xlsx\"")),
            content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                    schema = @Schema(type = "string", format = "binary")))
    @PostMapping(value = "/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public void export(HttpServletResponse response, @ParameterObject ErrorCategory errorCategory)
    {
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("error-categories.xlsx")
                .build()
                .toString());
        List<ErrorCategory> list = errorCategoryService.selectErrorCategoryList(errorCategory);
        ExcelUtil<ErrorCategory> util = new ExcelUtil<ErrorCategory>(ErrorCategory.class);
        util.exportExcel(response, list, "错误码类别数据");
    }

    /**
     * 获取错误码类别详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:query')")
    @Operation(summary = "获取错误码类别详情")
    @ApiResponse(responseCode = "200", description = "查询成功",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorCategoryDetailResponse.class)))
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
    @ApiResponse(responseCode = "200", description = "操作结果",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OperationResponse.class)))
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
    @ApiResponse(responseCode = "200", description = "操作结果",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OperationResponse.class)))
    @PutMapping
    public AjaxResult edit(@RequestBody ErrorCategory errorCategory)
    {
        errorCategory.setUpdateBy(getUsername());
        return toAjax(errorCategoryService.updateErrorCategory(errorCategory));
    }

    /** 批量修改错误码类别。 */
    @PreAuthorize("@ss.hasPermi('errorcode:category:edit')")
    @Log(title = "错误码类别", businessType = BusinessType.UPDATE)
    @Operation(summary = "批量修改错误码类别", description = "最多100条；任意一条失败时整批回滚")
    @ApiResponse(responseCode = "200", description = "操作结果",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = OperationResponse.class)))
    @PutMapping("/batch")
    public AjaxResult batchEdit(@RequestBody List<ErrorCategory> errorCategories)
    {
        String username = getUsername();
        if (errorCategories != null)
        {
            errorCategories.stream().filter(item -> item != null)
                    .forEach(item -> item.setUpdateBy(username));
        }
        return toAjax(errorCategoryService.updateErrorCategoryBatch(errorCategories));
    }

    /**
     * 删除错误码类别
     */
    @PreAuthorize("@ss.hasPermi('errorcode:category:remove')")
    @Log(title = "错误码类别", businessType = BusinessType.DELETE)
    @Operation(summary = "删除错误码类别", description = "支持批量删除，多个类别ID使用逗号分隔")
	@ApiResponse(responseCode = "200", description = "操作结果",
			content = @Content(mediaType = "application/json",
					schema = @Schema(implementation = OperationResponse.class)))
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "类别ID，多个使用逗号分隔", required = true)
                             @PathVariable Long[] ids)
    {
        return toAjax(errorCategoryService.deleteErrorCategoryByIds(ids));
    }
}
