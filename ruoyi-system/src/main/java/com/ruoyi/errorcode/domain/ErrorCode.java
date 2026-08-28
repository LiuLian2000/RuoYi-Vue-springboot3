package com.ruoyi.errorcode.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 错误码对象 error_code
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Schema(description = "错误码")
public class ErrorCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Schema(description = "主键ID", example = "1")
    private Long id;

    /** 所属系统ID */
    @Excel(name = "所属系统ID")
    @Schema(description = "所属系统ID", example = "1")
    private Long systemId;

    /** 所属系统名称 */
    @Excel(name = "所属系统名称")
    @Schema(description = "所属系统名称", example = "订单系统")
    private String systemName;

    /** 所属类别ID */
    @Excel(name = "所属类别ID")
    @Schema(description = "所属类别ID", example = "1")
    private Long categoryId;

    /** 所属类别名称 */
    @Excel(name = "所属类别名称")
    @Schema(description = "所属类别名称", example = "支付类")
    private String categoryName;

    /** 错误码(如11001) */
    @Excel(name = "错误码(如11001)")
    @Schema(description = "错误码", example = "ORDER_1001")
    private String code;

    /** 错误提示信息 */
    @Excel(name = "错误提示信息")
    @Schema(description = "错误提示信息", example = "订单不存在")
    private String message;

    /** 错误详细说明 */
    @Excel(name = "错误详细说明")
    @Schema(description = "错误详细说明", example = "根据订单号未查询到订单")
    private String description;

    /** 严重程度:1提示 2警告 3错误 4致命 */
    @Excel(name = "严重程度:1提示 2警告 3错误 4致命")
    @Schema(description = "严重程度：1提示 2警告 3错误 4致命", example = "3")
    private Integer severity;

    /** 严重程度名称 */
    @Excel(name = "严重程度名称")
    @Schema(description = "严重程度名称", example = "错误")
    private String severityName;

    /** 状态:1启用 0停用 */
    @Excel(name = "状态:1启用 0停用")
    @Schema(description = "状态：1启用 0停用", example = "1")
    private Integer status;

    /** 删除标志（0代表存在，删除时设置为本行ID） */
    @Schema(description = "逻辑删除标志", hidden = true)
    private Long delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setSystemId(Long systemId) 
    {
        this.systemId = systemId;
    }

    public Long getSystemId() 
    {
        return systemId;
    }

    public void setSystemName(String systemName)
    {
        this.systemName = systemName;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }

    public void setMessage(String message) 
    {
        this.message = message;
    }

    public String getMessage() 
    {
        return message;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setSeverity(Integer severity) 
    {
        this.severity = severity;
    }

    public Integer getSeverity() 
    {
        return severity;
    }

    public void setSeverityName(String severityName)
    {
        this.severityName = severityName;
    }

    public String getSeverityName()
    {
        return severityName;
    }

    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    public void setDelFlag(Long delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Long getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("systemId", getSystemId())
            .append("systemName", getSystemName())
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("code", getCode())
            .append("message", getMessage())
            .append("description", getDescription())
            .append("severity", getSeverity())
            .append("severityName", getSeverityName())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
