package com.ruoyi.errorcode.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
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
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.service.IErrorCodeYamlExportService;
import com.ruoyi.errorcode.service.IErrorCodeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 错误码Controller
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@RestController
@RequestMapping("/errorcode/code")
@Tag(name = "错误码管理", description = "错误码的增删改查接口")
public class ErrorCodeController extends BaseController
{
    private static final ObjectMapper YAML_MAPPER = createYamlMapper();

    @Autowired
    private IErrorCodeService errorCodeService;

    @Autowired
    private IErrorCodeYamlExportService errorCodeYamlExportService;

    /**
     * 查询错误码列表
     */
    @PreAuthorize("@ss.hasPermi('errorcode:code:list')")
    @Operation(summary = "查询错误码列表", parameters = {
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
    public TableDataInfo list(@ParameterObject ErrorCode errorCode)
    {
        startPage();
        List<ErrorCode> list = errorCodeService.selectErrorCodeList(errorCode);
        return getDataTable(list);
    }

    /**
     * 导出错误码列表
     */
    @PreAuthorize("@ss.hasPermi('errorcode:code:export')")
    @Log(title = "错误码", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出错误码列表")
    @PostMapping("/export")
    public void export(HttpServletResponse response, @ParameterObject ErrorCode errorCode)
    {
        List<ErrorCode> list = errorCodeService.selectErrorCodeList(errorCode);
        ExcelUtil<ErrorCode> util = new ExcelUtil<ErrorCode>(ErrorCode.class);
        util.exportExcel(response, list, "错误码数据");
    }

    /**
     * 导出所有有效启用的错误码 YAML 配置
     */
    @PreAuthorize("@ss.hasPermi('errorcode:code:export')")
    @Log(title = "错误码", businessType = BusinessType.EXPORT)
    @Operation(summary = "导出错误码 YAML 配置")
    @PostMapping("/exportYaml")
    public void exportYaml(HttpServletResponse response) throws IOException
    {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType("application/x-yaml;charset=UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename("error-codes.yml", StandardCharsets.UTF_8)
                .build()
                .toString());
        YAML_MAPPER.writeValue(response.getOutputStream(), errorCodeYamlExportService.buildExportData());
    }

    /**
     * 获取错误码详细信息
     */
    @PreAuthorize("@ss.hasPermi('errorcode:code:query')")
    @Operation(summary = "获取错误码详情")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@Parameter(description = "错误码ID", required = true)
                              @PathVariable("id") Long id)
    {
        return success(errorCodeService.selectErrorCodeById(id));
    }

    /**
     * 新增错误码
     */
    @PreAuthorize("@ss.hasPermi('errorcode:code:add')")
    @Log(title = "错误码", businessType = BusinessType.INSERT)
    @Operation(summary = "新增错误码")
    @PostMapping
    public AjaxResult add(@RequestBody ErrorCode errorCode)
    {
        errorCode.setCreateBy(getUsername());
        return toAjax(errorCodeService.insertErrorCode(errorCode));
    }

    /**
     * 修改错误码
     */
    @PreAuthorize("@ss.hasPermi('errorcode:code:edit')")
    @Log(title = "错误码", businessType = BusinessType.UPDATE)
    @Operation(summary = "修改错误码")
    @PutMapping
    public AjaxResult edit(@RequestBody ErrorCode errorCode)
    {
        errorCode.setUpdateBy(getUsername());
        return toAjax(errorCodeService.updateErrorCode(errorCode));
    }

    /**
     * 删除错误码
     */
    @PreAuthorize("@ss.hasPermi('errorcode:code:remove')")
    @Log(title = "错误码", businessType = BusinessType.DELETE)
    @Operation(summary = "删除错误码", description = "支持批量删除，多个错误码ID使用逗号分隔")
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@Parameter(description = "错误码ID，多个使用逗号分隔", required = true)
                             @PathVariable Long[] ids)
    {
        return toAjax(errorCodeService.deleteErrorCodeByIds(ids));
    }

    private static ObjectMapper createYamlMapper()
    {
        YAMLFactory yamlFactory = YAMLFactory.builder()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER)
                .enable(YAMLGenerator.Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS)
                .build();
        ObjectMapper mapper = new ObjectMapper(yamlFactory);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }
}
