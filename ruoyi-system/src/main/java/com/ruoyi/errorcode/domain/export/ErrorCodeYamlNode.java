package com.ruoyi.errorcode.domain.export;

/**
 * 错误码 YAML 节点。
 */
public class ErrorCodeYamlNode
{
    private String message;

    private String description;

    private Integer severity;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getSeverity()
    {
        return severity;
    }

    public void setSeverity(Integer severity)
    {
        this.severity = severity;
    }
}
