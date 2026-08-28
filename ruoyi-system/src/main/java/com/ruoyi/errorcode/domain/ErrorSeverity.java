package com.ruoyi.errorcode.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 错误码严重程度对象 error_severity
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Schema(description = "错误码严重程度")
public class ErrorSeverity extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Schema(description = "主键ID", example = "1")
    private Long id;

    /** 严重程度代码:1提示 2警告 3错误 4致命 */
    @Excel(name = "严重程度代码:1提示 2警告 3错误 4致命")
    @Schema(description = "严重程度代码：1提示 2警告 3错误 4致命", example = "3")
    private Integer severityCode;

    /** 严重程度名称 */
    @Excel(name = "严重程度名称")
    @Schema(description = "严重程度名称", example = "错误")
    private String severityName;

    /** 描述 */
    @Excel(name = "描述")
    @Schema(description = "描述", example = "一般业务错误")
    private String description;

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

    public void setSeverityCode(Integer severityCode) 
    {
        this.severityCode = severityCode;
    }

    public Integer getSeverityCode() 
    {
        return severityCode;
    }

    public void setSeverityName(String severityName) 
    {
        this.severityName = severityName;
    }

    public String getSeverityName() 
    {
        return severityName;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
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
            .append("severityCode", getSeverityCode())
            .append("severityName", getSeverityName())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
