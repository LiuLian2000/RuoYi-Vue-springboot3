package com.ruoyi.errorcode.domain.export;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 错误码类别 YAML 节点。
 */
public class CategoryYamlNode
{
    private String name;

    private String description;

    private Map<String, ErrorCodeYamlNode> errorCodes = new LinkedHashMap<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Map<String, ErrorCodeYamlNode> getErrorCodes()
    {
        return errorCodes;
    }

    public void setErrorCodes(Map<String, ErrorCodeYamlNode> errorCodes)
    {
        this.errorCodes = errorCodes;
    }
}
