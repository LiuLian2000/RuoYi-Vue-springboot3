package com.ruoyi.errorcode.domain.response;

import java.util.List;
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.domain.ErrorSystem;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 错误码模块的 OpenAPI 响应模型。
 *
 * <p>这些类型只负责描述 AjaxResult 和 TableDataInfo 的真实 JSON 结构，
 * 不参与接口运行，也不改变前端收到的响应。</p>
 */
public final class ErrorCodeOpenApiResponses
{
    private ErrorCodeOpenApiResponses()
    {
    }

    /** 新增、修改和删除接口的通用响应。 */
    @Schema(name = "ErrorCodeOperationResponse", description = "错误码模块通用操作响应")
    public static class OperationResponse
    {
        @Schema(description = "业务状态码：200表示成功，500表示失败", example = "200")
        private Integer code;

        @Schema(description = "响应消息", example = "操作成功")
        private String msg;

        public Integer getCode()
        {
            return code;
        }

        public void setCode(Integer code)
        {
            this.code = code;
        }

        public String getMsg()
        {
            return msg;
        }

        public void setMsg(String msg)
        {
            this.msg = msg;
        }
    }

    /** AjaxResult 详情响应的公共结构。 */
    public static class DetailResponse<T>
    {
        @Schema(description = "业务状态码：200表示成功", example = "200")
        private Integer code;

        @Schema(description = "响应消息", example = "操作成功")
        private String msg;

        @Schema(description = "详情数据")
        private T data;

        public Integer getCode()
        {
            return code;
        }

        public void setCode(Integer code)
        {
            this.code = code;
        }

        public String getMsg()
        {
            return msg;
        }

        public void setMsg(String msg)
        {
            this.msg = msg;
        }

        public T getData()
        {
            return data;
        }

        public void setData(T data)
        {
            this.data = data;
        }
    }

    /** TableDataInfo 分页响应的公共结构。 */
    public static class PageResponse<T>
    {
        @Schema(description = "业务状态码：200表示成功", example = "200")
        private Integer code;

        @Schema(description = "响应消息", example = "查询成功")
        private String msg;

        @Schema(description = "总记录数", example = "20")
        private Long total;

        @Schema(description = "当前页数据")
        private List<T> rows;

        public Integer getCode()
        {
            return code;
        }

        public void setCode(Integer code)
        {
            this.code = code;
        }

        public String getMsg()
        {
            return msg;
        }

        public void setMsg(String msg)
        {
            this.msg = msg;
        }

        public Long getTotal()
        {
            return total;
        }

        public void setTotal(Long total)
        {
            this.total = total;
        }

        public List<T> getRows()
        {
            return rows;
        }

        public void setRows(List<T> rows)
        {
            this.rows = rows;
        }
    }

    @Schema(name = "ErrorSystemDetailResponse", description = "错误码系统详情响应")
    public static class ErrorSystemDetailResponse extends DetailResponse<ErrorSystem>
    {
    }

    @Schema(name = "ErrorCategoryDetailResponse", description = "错误码类别详情响应")
    public static class ErrorCategoryDetailResponse extends DetailResponse<ErrorCategory>
    {
    }

    @Schema(name = "ErrorSeverityDetailResponse", description = "错误码严重程度详情响应")
    public static class ErrorSeverityDetailResponse extends DetailResponse<ErrorSeverity>
    {
    }

    @Schema(name = "ErrorCodeDetailResponse", description = "错误码详情响应")
    public static class ErrorCodeDetailResponse extends DetailResponse<ErrorCode>
    {
    }

    @Schema(name = "ErrorSystemPageResponse", description = "错误码系统分页响应")
    public static class ErrorSystemPageResponse extends PageResponse<ErrorSystem>
    {
    }

    @Schema(name = "ErrorCategoryPageResponse", description = "错误码类别分页响应")
    public static class ErrorCategoryPageResponse extends PageResponse<ErrorCategory>
    {
    }

    @Schema(name = "ErrorSeverityPageResponse", description = "错误码严重程度分页响应")
    public static class ErrorSeverityPageResponse extends PageResponse<ErrorSeverity>
    {
    }

    @Schema(name = "ErrorCodePageResponse", description = "错误码分页响应")
    public static class ErrorCodePageResponse extends PageResponse<ErrorCode>
    {
    }
}
