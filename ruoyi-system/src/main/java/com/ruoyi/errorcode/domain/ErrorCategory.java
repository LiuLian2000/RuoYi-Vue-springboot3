package com.ruoyi.errorcode.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 错误码类别对象 error_category
 * 
 * @author ruoyi
 * @date 2026-08-26
 */
@Schema(description = "错误码类别")
public class ErrorCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Schema(description = "主键ID", example = "1")
    private Long id;

    /** 所属系统ID */
    @Excel(name = "所属系统ID")
    @Schema(description = "所属系统ID", example = "1")
    private Long systemId;

    /** 所属系统名称（联表查询字段） */
    @Excel(name = "所属系统名称")
    @Schema(description = "所属系统名称", example = "订单系统")
    private String systemName;

    /** 类别编码 */
    @Excel(name = "类别编码")
    @Schema(description = "类别编码", example = "PAYMENT")
    private String categoryCode;

    /** 类别名称 */
    @Excel(name = "类别名称")
    @Schema(description = "类别名称", example = "支付类")
    private String categoryName;

    /** 类别描述 */
    @Excel(name = "类别描述")
    @Schema(description = "类别描述", example = "支付相关错误码")
    private String description;

    /** 状态:1启用 0停用 */
    @Excel(name = "状态:1启用 0停用")
    @Schema(description = "状态：1启用 0停用", example = "1")
    private Integer status;

    /** 乐观锁版本号 */
    @Schema(description = "乐观锁版本号", example = "0")
    private Integer version;

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

    public void setCategoryCode(String categoryCode) 
    {
        this.categoryCode = categoryCode;
    }

    public String getCategoryCode() 
    {
        return categoryCode;
    }

    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
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

    public void setVersion(Integer version)
    {
        this.version = version;
    }

    public Integer getVersion()
    {
        return version;
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
            .append("categoryCode", getCategoryCode())
            .append("categoryName", getCategoryName())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("version", getVersion())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
