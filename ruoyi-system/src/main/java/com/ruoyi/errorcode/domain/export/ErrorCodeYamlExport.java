package com.ruoyi.errorcode.domain.export;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 错误码 YAML 导出根节点。
 */
public class ErrorCodeYamlExport
{
    private Map<String, SeverityYamlNode> severities = new LinkedHashMap<>();

    private Map<String, SystemYamlNode> systems = new LinkedHashMap<>();

    public Map<String, SeverityYamlNode> getSeverities()
    {
        return severities;
    }

    public void setSeverities(Map<String, SeverityYamlNode> severities)
    {
        this.severities = severities;
    }

    public Map<String, SystemYamlNode> getSystems()
    {
        return systems;
    }

    public void setSystems(Map<String, SystemYamlNode> systems)
    {
        this.systems = systems;
    }
}
