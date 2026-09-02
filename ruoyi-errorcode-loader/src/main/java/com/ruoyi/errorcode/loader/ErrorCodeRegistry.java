package com.ruoyi.errorcode.loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * 全量错误码注册表。加载完成后内容不可变，可安全地被多个线程查询。
 */
public final class ErrorCodeRegistry
{
    private final List<ErrorCodeDefinition> all;
    private final Map<SystemErrorKey, ErrorCodeDefinition> bySystemAndCode;
    private final Map<String, List<ErrorCodeDefinition>> byErrorCode;

    ErrorCodeRegistry(List<ErrorCodeDefinition> definitions)
    {
        List<ErrorCodeDefinition> values = List.copyOf(definitions);
        Map<SystemErrorKey, ErrorCodeDefinition> systemCodeIndex = new LinkedHashMap<>();
        Map<String, List<ErrorCodeDefinition>> errorCodeIndex = new LinkedHashMap<>();

        for (ErrorCodeDefinition definition : values)
        {
            SystemErrorKey key = new SystemErrorKey(
                    definition.systemCode(), definition.errorCode());
            if (systemCodeIndex.putIfAbsent(key, definition) != null)
            {
                throw new ErrorCodeLoadException("同一系统存在重复错误码："
                        + definition.systemCode() + "/" + definition.errorCode());
            }
            errorCodeIndex.computeIfAbsent(definition.errorCode(), ignored -> new ArrayList<>())
                    .add(definition);
        }

        Map<String, List<ErrorCodeDefinition>> immutableCodeIndex = new LinkedHashMap<>();
        errorCodeIndex.forEach((code, items) -> immutableCodeIndex.put(code, List.copyOf(items)));

        this.all = values;
        this.bySystemAndCode = Collections.unmodifiableMap(systemCodeIndex);
        this.byErrorCode = Collections.unmodifiableMap(immutableCodeIndex);
    }

    /** 返回 YAML 中的全部错误码。 */
    public List<ErrorCodeDefinition> getAll()
    {
        return all;
    }

    /** 系统编码和错误码在当前数据模型中可以唯一定位一条记录。 */
    public Optional<ErrorCodeDefinition> findBySystemCodeAndErrorCode(
            String systemCode, String errorCode)
    {
        return Optional.ofNullable(bySystemAndCode.get(
                new SystemErrorKey(systemCode, errorCode)));
    }

    public ErrorCodeDefinition requireBySystemCodeAndErrorCode(
            String systemCode, String errorCode)
    {
        return findBySystemCodeAndErrorCode(systemCode, errorCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "未找到错误码：" + systemCode + "/" + errorCode));
    }

    /** 错误码只在系统内唯一，因此单独按错误码查询可能返回多条。 */
    public List<ErrorCodeDefinition> findByErrorCode(String errorCode)
    {
        return byErrorCode.getOrDefault(errorCode, List.of());
    }

    /** 按系统名称、类别名称和错误码查询；名称不作为唯一键，所以返回列表。 */
    public List<ErrorCodeDefinition> findByNames(
            String systemName, String categoryName, String errorCode)
    {
        return query(ErrorCodeQuery.create()
                .systemName(systemName)
                .categoryName(categoryName)
                .errorCode(errorCode));
    }

    /** 使用多个可选条件组合查询。编码、名称采用精确匹配，keyword 采用包含匹配。 */
    public List<ErrorCodeDefinition> query(ErrorCodeQuery query)
    {
        Objects.requireNonNull(query, "query不能为空");
        return all.stream()
                .filter(item -> equalsIfPresent(query.getSystemCode(), item.systemCode()))
                .filter(item -> equalsIfPresent(query.getSystemName(), item.systemName()))
                .filter(item -> equalsIfPresent(query.getCategoryCode(), item.categoryCode()))
                .filter(item -> equalsIfPresent(query.getCategoryName(), item.categoryName()))
                .filter(item -> equalsIfPresent(query.getErrorCode(), item.errorCode()))
                .filter(item -> equalsIfPresent(query.getSeverityCode(), item.severityCode()))
                .filter(item -> equalsIfPresent(query.getSeverityName(), item.severityName()))
                .filter(item -> containsKeyword(query.getKeyword(), item))
                .toList();
    }

    private boolean equalsIfPresent(Object condition, Object value)
    {
        return condition == null || Objects.equals(condition, value);
    }

    private boolean containsKeyword(String keyword, ErrorCodeDefinition item)
    {
        if (keyword == null || keyword.isBlank())
        {
            return true;
        }
        return contains(item.message(), keyword) || contains(item.description(), keyword);
    }

    private boolean contains(String value, String keyword)
    {
        return value != null && value.contains(keyword);
    }

    private record SystemErrorKey(String systemCode, String errorCode)
    {
    }
}
