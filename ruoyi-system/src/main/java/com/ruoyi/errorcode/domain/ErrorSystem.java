package com.ruoyi.errorcode.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 错误码所属系统对象 error_system
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Schema(description = "错误码所属系统")
public class ErrorSystem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Schema(description = "主键ID", example = "1")
    private Long id;

    /** 系统编码 */
    @Excel(name = "系统编码")
    @Schema(description = "系统编码", example = "ORDER")
    private String systemCode;

    /** 系统名称 */
    @Excel(name = "系统名称")
    @Schema(description = "系统名称", example = "订单系统")
    private String systemName;

    /** 系统描述 */
    @Excel(name = "系统描述")
    @Schema(description = "系统描述", example = "订单系统错误码")
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

    public void setSystemCode(String systemCode) 
    {
        this.systemCode = systemCode;
    }

    public String getSystemCode() 
    {
        return systemCode;
    }

    public void setSystemName(String systemName) 
    {
        this.systemName = systemName;
    }

    public String getSystemName() 
    {
        return systemName;
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
            .append("systemCode", getSystemCode())
            .append("systemName", getSystemName())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
