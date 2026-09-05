package com.ruoyi.errorcode.loader;

/**
 * 从 YAML 展开后的完整错误码信息。
 */
public record ErrorCodeDefinition(
        String systemCode,
        String systemName,
        String systemDescription,
        String categoryCode,
        String categoryName,
        String categoryDescription,
        String errorCode,
        String message,
        String description,
        Integer severityCode,
        String severityName,
        String severityDescription)
{
}
