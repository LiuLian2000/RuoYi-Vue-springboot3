package com.ruoyi.errorcode.loader;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

/**
 * 读取错误码管理系统导出的 YAML，并构建全量查询注册表。
 */
public final class ErrorCodeLoader
{
    private final YAMLMapper yamlMapper;

    public ErrorCodeLoader()
    {
        this.yamlMapper = YAMLMapper.builder()
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .build();
    }

    public ErrorCodeRegistry load(Path path)
    {
        Objects.requireNonNull(path, "path不能为空");
        try (InputStream inputStream = Files.newInputStream(path))
        {
            return load(inputStream);
        }
        catch (IOException exception)
        {
            throw new ErrorCodeLoadException("读取错误码YAML失败：" + path, exception);
        }
    }

    /** 调用方负责关闭传入的输入流。 */
    public ErrorCodeRegistry load(InputStream inputStream)
    {
        Objects.requireNonNull(inputStream, "inputStream不能为空");
        try
        {
            YamlDocument document = yamlMapper.readValue(inputStream, YamlDocument.class);
            return flatten(document);
        }
        catch (IOException exception)
        {
            throw new ErrorCodeLoadException("解析错误码YAML失败", exception);
        }
    }

    public ErrorCodeRegistry loadClasspath(String resourceName)
    {
        return loadClasspath(resourceName, Thread.currentThread().getContextClassLoader());
    }

    public ErrorCodeRegistry loadClasspath(String resourceName, ClassLoader classLoader)
    {
        Objects.requireNonNull(resourceName, "resourceName不能为空");
        Objects.requireNonNull(classLoader, "classLoader不能为空");
        try (InputStream inputStream = classLoader.getResourceAsStream(resourceName))
        {
            if (inputStream == null)
            {
                throw new ErrorCodeLoadException("找不到错误码YAML：" + resourceName);
            }
            return load(inputStream);
        }
        catch (IOException exception)
        {
            throw new ErrorCodeLoadException("关闭错误码YAML输入流失败：" + resourceName, exception);
        }
    }

    private ErrorCodeRegistry flatten(YamlDocument document)
    {
        if (document == null)
        {
            throw new ErrorCodeLoadException("错误码YAML内容为空");
        }

        Map<String, SeverityNode> severities = requireMap(document.getSeverities(), "severities");
        Map<String, SystemNode> systems = requireMap(document.getSystems(), "systems");
        List<ErrorCodeDefinition> definitions = new ArrayList<>();

        for (Map.Entry<String, SystemNode> systemEntry : systems.entrySet())
        {
            String systemCode = requireText(systemEntry.getKey(), "系统编码");
            SystemNode system = requireValue(systemEntry.getValue(), "系统 " + systemCode);
            String systemName = requireText(system.getName(), "系统名称 " + systemCode);

            for (Map.Entry<String, CategoryNode> categoryEntry
                    : requireMap(system.getCategories(), "系统类别 " + systemCode).entrySet())
            {
                String categoryCode = requireText(categoryEntry.getKey(), "类别编码");
                CategoryNode category = requireValue(categoryEntry.getValue(),
                        "类别 " + systemCode + "/" + categoryCode);
                String categoryName = requireText(category.getName(),
                        "类别名称 " + systemCode + "/" + categoryCode);

                for (Map.Entry<String, ErrorNode> errorEntry
                        : requireMap(category.getErrorCodes(),
                                "类别错误码 " + systemCode + "/" + categoryCode).entrySet())
                {
                    String errorCode = requireText(errorEntry.getKey(), "错误码");
                    ErrorNode error = requireValue(errorEntry.getValue(),
                            "错误码 " + systemCode + "/" + errorCode);
                    String message = requireText(error.getMessage(),
                            "错误提示 " + systemCode + "/" + errorCode);
                    if (error.getSeverity() == null)
                    {
                        throw new ErrorCodeLoadException("错误码未配置严重程度："
                                + systemCode + "/" + errorCode);
                    }

                    SeverityNode severity = severities.get(String.valueOf(error.getSeverity()));
                    if (severity == null)
                    {
                        throw new ErrorCodeLoadException("错误码引用了不存在的严重程度："
                                + systemCode + "/" + errorCode + " -> " + error.getSeverity());
                    }

                    definitions.add(new ErrorCodeDefinition(
                            systemCode,
                            systemName,
                            system.getDescription(),
                            categoryCode,
                            categoryName,
                            category.getDescription(),
                            errorCode,
                            message,
                            error.getDescription(),
                            error.getSeverity(),
                            requireText(severity.getName(), "严重程度名称 " + error.getSeverity()),
                            severity.getDescription()));
                }
            }
        }
        return new ErrorCodeRegistry(definitions);
    }

    private <K, V> Map<K, V> requireMap(Map<K, V> value, String field)
    {
        if (value == null)
        {
            throw new ErrorCodeLoadException(field + "不能为空");
        }
        return value;
    }

    private <T> T requireValue(T value, String field)
    {
        if (value == null)
        {
            throw new ErrorCodeLoadException(field + "不能为空");
        }
        return value;
    }

    private String requireText(String value, String field)
    {
        if (value == null || value.isBlank())
        {
            throw new ErrorCodeLoadException(field + "不能为空");
        }
        return value;
    }

    public static final class YamlDocument
    {
        private Map<String, SeverityNode> severities = new LinkedHashMap<>();
        private Map<String, SystemNode> systems = new LinkedHashMap<>();

        public Map<String, SeverityNode> getSeverities()
        {
            return severities;
        }

        public void setSeverities(Map<String, SeverityNode> severities)
        {
            this.severities = severities;
        }

        public Map<String, SystemNode> getSystems()
        {
            return systems;
        }

        public void setSystems(Map<String, SystemNode> systems)
        {
            this.systems = systems;
        }
    }

    public static final class SeverityNode
    {
        private String name;
        private String description;

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
    }

    public static final class SystemNode
    {
        private String name;
        private String description;
        private Map<String, CategoryNode> categories = new LinkedHashMap<>();

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

        public Map<String, CategoryNode> getCategories()
        {
            return categories;
        }

        public void setCategories(Map<String, CategoryNode> categories)
        {
            this.categories = categories;
        }
    }

    public static final class CategoryNode
    {
        private String name;
        private String description;
        private Map<String, ErrorNode> errorCodes = new LinkedHashMap<>();

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

        public Map<String, ErrorNode> getErrorCodes()
        {
            return errorCodes;
        }

        public void setErrorCodes(Map<String, ErrorNode> errorCodes)
        {
            this.errorCodes = errorCodes;
        }
    }

    public static final class ErrorNode
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
}
