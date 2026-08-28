package com.ruoyi.errorcode.domain.export;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 系统 YAML 节点。
 */
public class SystemYamlNode
{
    private String name;

    private String description;

    private Map<String, CategoryYamlNode> categories = new LinkedHashMap<>();

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

    public Map<String, CategoryYamlNode> getCategories()
    {
        return categories;
    }

    public void setCategories(Map<String, CategoryYamlNode> categories)
    {
        this.categories = categories;
    }
}
