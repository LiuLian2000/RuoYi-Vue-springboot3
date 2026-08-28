package com.ruoyi.errorcode.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.domain.ErrorSystem;
import com.ruoyi.errorcode.domain.export.CategoryYamlNode;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlExport;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlNode;
import com.ruoyi.errorcode.domain.export.SeverityYamlNode;
import com.ruoyi.errorcode.domain.export.SystemYamlNode;
import com.ruoyi.errorcode.service.IErrorCategoryService;
import com.ruoyi.errorcode.service.IErrorCodeService;
import com.ruoyi.errorcode.service.IErrorCodeYamlExportService;
import com.ruoyi.errorcode.service.IErrorSeverityService;
import com.ruoyi.errorcode.service.IErrorSystemService;

/**
 * 错误码 YAML 导出服务实现。
 */
@Service
public class ErrorCodeYamlExportServiceImpl implements IErrorCodeYamlExportService
{
    private static final Integer ENABLED = 1;

    @Autowired
    private IErrorSystemService errorSystemService;

    @Autowired
    private IErrorCategoryService errorCategoryService;

    @Autowired
    private IErrorSeverityService errorSeverityService;

    @Autowired
    private IErrorCodeService errorCodeService;

    @Override
    public ErrorCodeYamlExport buildExportData()
    {
        ErrorCodeYamlExport exportData = new ErrorCodeYamlExport();

        Map<Long, ErrorSystem> systemsById = loadEnabledSystems();
        Map<Long, ErrorCategory> categoriesById = loadEnabledCategories(systemsById);
        Map<Integer, ErrorSeverity> severitiesByCode = loadEnabledSeverities(exportData);
        List<ErrorCode> errorCodes = loadEnabledErrorCodes(systemsById, categoriesById, severitiesByCode);

        errorCodes.sort(Comparator
                .comparing((ErrorCode item) -> systemsById.get(item.getSystemId()).getSystemCode())
                .thenComparing(item -> categoriesById.get(item.getCategoryId()).getCategoryCode())
                .thenComparing(ErrorCode::getCode));

        Set<String> exportedErrorCodes = new HashSet<>();
        for (ErrorCode errorCode : errorCodes)
        {
            ErrorSystem system = systemsById.get(errorCode.getSystemId());
            ErrorCategory category = categoriesById.get(errorCode.getCategoryId());

            if (!system.getId().equals(category.getSystemId()))
            {
                throw new ServiceException("错误码 " + errorCode.getCode() + " 所属系统与类别所属系统不一致");
            }

            String errorCodeKey = system.getSystemCode() + ":" + errorCode.getCode();
            if (!exportedErrorCodes.add(errorCodeKey))
            {
                throw new ServiceException("系统 " + system.getSystemCode() + " 存在重复错误码：" + errorCode.getCode());
            }

            SystemYamlNode systemNode = exportData.getSystems().computeIfAbsent(system.getSystemCode(), key -> {
                SystemYamlNode node = new SystemYamlNode();
                node.setName(system.getSystemName());
                node.setDescription(system.getDescription());
                return node;
            });

            CategoryYamlNode categoryNode = systemNode.getCategories().computeIfAbsent(category.getCategoryCode(), key -> {
                CategoryYamlNode node = new CategoryYamlNode();
                node.setName(category.getCategoryName());
                node.setDescription(category.getDescription());
                return node;
            });

            ErrorCodeYamlNode errorCodeNode = new ErrorCodeYamlNode();
            errorCodeNode.setMessage(errorCode.getMessage());
            errorCodeNode.setDescription(errorCode.getDescription());
            errorCodeNode.setSeverity(errorCode.getSeverity());
            categoryNode.getErrorCodes().put(errorCode.getCode(), errorCodeNode);
        }

        return exportData;
    }

    private Map<Long, ErrorSystem> loadEnabledSystems()
    {
        ErrorSystem query = new ErrorSystem();
        query.setStatus(ENABLED);
        List<ErrorSystem> systems = new ArrayList<>(errorSystemService.selectErrorSystemList(query));
        systems.sort(Comparator.comparing(ErrorSystem::getSystemCode,
                Comparator.nullsLast(String::compareTo)));

        Map<Long, ErrorSystem> systemsById = new HashMap<>();
        Set<String> systemCodes = new HashSet<>();
        for (ErrorSystem system : systems)
        {
            if (!ENABLED.equals(system.getStatus()))
            {
                continue;
            }
            requireText(system.getSystemCode(), "存在未配置系统编码的启用系统");
            if (!systemCodes.add(system.getSystemCode()))
            {
                throw new ServiceException("存在重复系统编码：" + system.getSystemCode());
            }
            systemsById.put(system.getId(), system);
        }
        return systemsById;
    }

    private Map<Long, ErrorCategory> loadEnabledCategories(Map<Long, ErrorSystem> systemsById)
    {
        ErrorCategory query = new ErrorCategory();
        query.setStatus(ENABLED);
        List<ErrorCategory> categories = errorCategoryService.selectErrorCategoryList(query);

        Map<Long, ErrorCategory> categoriesById = new HashMap<>();
        Set<String> categoryCodes = new HashSet<>();
        for (ErrorCategory category : categories)
        {
            if (!ENABLED.equals(category.getStatus()) || !systemsById.containsKey(category.getSystemId()))
            {
                continue;
            }
            requireText(category.getCategoryCode(), "存在未配置类别编码的启用类别");
            String categoryKey = category.getSystemId() + ":" + category.getCategoryCode();
            if (!categoryCodes.add(categoryKey))
            {
                throw new ServiceException("同一系统下存在重复类别编码：" + category.getCategoryCode());
            }
            categoriesById.put(category.getId(), category);
        }
        return categoriesById;
    }

    private Map<Integer, ErrorSeverity> loadEnabledSeverities(ErrorCodeYamlExport exportData)
    {
        ErrorSeverity query = new ErrorSeverity();
        query.setStatus(ENABLED);
        List<ErrorSeverity> severities = new ArrayList<>(errorSeverityService.selectErrorSeverityList(query));
        severities.sort(Comparator.comparing(ErrorSeverity::getSeverityCode,
                Comparator.nullsLast(Integer::compareTo)));

        Map<Integer, ErrorSeverity> severitiesByCode = new HashMap<>();
        for (ErrorSeverity severity : severities)
        {
            if (!ENABLED.equals(severity.getStatus()))
            {
                continue;
            }
            if (severity.getSeverityCode() == null)
            {
                throw new ServiceException("存在未配置严重程度编码的启用严重程度");
            }
            if (severitiesByCode.putIfAbsent(severity.getSeverityCode(), severity) != null)
            {
                throw new ServiceException("存在重复严重程度编码：" + severity.getSeverityCode());
            }

            SeverityYamlNode node = new SeverityYamlNode();
            node.setName(severity.getSeverityName());
            node.setDescription(severity.getDescription());
            exportData.getSeverities().put(String.valueOf(severity.getSeverityCode()), node);
        }
        return severitiesByCode;
    }

    private List<ErrorCode> loadEnabledErrorCodes(Map<Long, ErrorSystem> systemsById,
            Map<Long, ErrorCategory> categoriesById, Map<Integer, ErrorSeverity> severitiesByCode)
    {
        ErrorCode query = new ErrorCode();
        query.setStatus(ENABLED);
        List<ErrorCode> rows = errorCodeService.selectErrorCodeList(query);
        List<ErrorCode> result = new ArrayList<>();

        for (ErrorCode row : rows)
        {
            if (!ENABLED.equals(row.getStatus())
                    || !systemsById.containsKey(row.getSystemId())
                    || !categoriesById.containsKey(row.getCategoryId())
                    || !severitiesByCode.containsKey(row.getSeverity()))
            {
                continue;
            }
            requireText(row.getCode(), "存在未配置错误码的启用记录");
            result.add(row);
        }
        return result;
    }

    private void requireText(String value, String message)
    {
        if (value == null || value.isBlank())
        {
            throw new ServiceException(message);
        }
    }
}
