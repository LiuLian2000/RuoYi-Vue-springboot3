package com.ruoyi.errorcode.loader;

/**
 * 错误码组合查询条件；未设置的字段不参与过滤。
 */
public final class ErrorCodeQuery
{
    private String systemCode;
    private String systemName;
    private String categoryCode;
    private String categoryName;
    private String errorCode;
    private Integer severityCode;
    private String severityName;
    private String keyword;

    public static ErrorCodeQuery create()
    {
        return new ErrorCodeQuery();
    }

    public String getSystemCode()
    {
        return systemCode;
    }

    public ErrorCodeQuery systemCode(String systemCode)
    {
        this.systemCode = systemCode;
        return this;
    }

    public String getSystemName()
    {
        return systemName;
    }

    public ErrorCodeQuery systemName(String systemName)
    {
        this.systemName = systemName;
        return this;
    }

    public String getCategoryCode()
    {
        return categoryCode;
    }

    public ErrorCodeQuery categoryCode(String categoryCode)
    {
        this.categoryCode = categoryCode;
        return this;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public ErrorCodeQuery categoryName(String categoryName)
    {
        this.categoryName = categoryName;
        return this;
    }

    public String getErrorCode()
    {
        return errorCode;
    }

    public ErrorCodeQuery errorCode(String errorCode)
    {
        this.errorCode = errorCode;
        return this;
    }

    public Integer getSeverityCode()
    {
        return severityCode;
    }

    public ErrorCodeQuery severityCode(Integer severityCode)
    {
        this.severityCode = severityCode;
        return this;
    }

    public String getSeverityName()
    {
        return severityName;
    }

    public ErrorCodeQuery severityName(String severityName)
    {
        this.severityName = severityName;
        return this;
    }

    public String getKeyword()
    {
        return keyword;
    }

    /** 同时模糊匹配错误提示和错误描述。 */
    public ErrorCodeQuery keyword(String keyword)
    {
        this.keyword = keyword;
        return this;
    }
}
