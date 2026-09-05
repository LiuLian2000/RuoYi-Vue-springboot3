package com.ruoyi.errorcode.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.domain.export.CategoryYamlNode;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlExport;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlNode;
import com.ruoyi.errorcode.domain.export.SeverityYamlNode;
import com.ruoyi.errorcode.domain.export.SystemYamlNode;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.service.IErrorCodeYamlExportService;
import com.ruoyi.errorcode.service.IErrorSeverityService;

/**
 * 错误码 YAML 导出服务实现。
 */
@Service
public class ErrorCodeYamlExportServiceImpl implements IErrorCodeYamlExportService
{
    private static final Integer ENABLED = 1;

    @Autowired
    private ErrorCodeMapper errorCodeMapper;

    @Autowired
    private IErrorSeverityService errorSeverityService;

    @Override
    public ErrorCodeYamlExport buildExportData()
    {
        ErrorCodeYamlExport exportData = new ErrorCodeYamlExport();
        loadEnabledSeverities(exportData);

        List<ErrorCode> errorCodes = errorCodeMapper.selectEnabledErrorCodeExportList();
        for (ErrorCode errorCode : errorCodes)
        {
            SystemYamlNode systemNode = exportData.getSystems().computeIfAbsent(
                    errorCode.getSystemCode(), key -> createSystemNode(errorCode));

            CategoryYamlNode categoryNode = systemNode.getCategories().computeIfAbsent(
                    errorCode.getCategoryCode(), key -> createCategoryNode(errorCode));

            ErrorCodeYamlNode errorCodeNode = new ErrorCodeYamlNode();
            errorCodeNode.setMessage(errorCode.getMessage());
            errorCodeNode.setDescription(errorCode.getDescription());
            errorCodeNode.setSeverity(errorCode.getSeverityCode());
            categoryNode.getErrorCodes().put(errorCode.getCode(), errorCodeNode);
        }

        return exportData;
    }

    private SystemYamlNode createSystemNode(ErrorCode errorCode)
    {
        SystemYamlNode node = new SystemYamlNode();
        node.setName(errorCode.getSystemName());
        node.setDescription(errorCode.getSystemDescription());
        return node;
    }

    private CategoryYamlNode createCategoryNode(ErrorCode errorCode)
    {
        CategoryYamlNode node = new CategoryYamlNode();
        node.setName(errorCode.getCategoryName());
        node.setDescription(errorCode.getCategoryDescription());
        return node;
    }

    private void loadEnabledSeverities(ErrorCodeYamlExport exportData)
    {
        ErrorSeverity query = new ErrorSeverity();
        query.setStatus(ENABLED);
        List<ErrorSeverity> severities = new ArrayList<>(
                errorSeverityService.selectErrorSeverityList(query));
        severities.sort(Comparator.comparing(ErrorSeverity::getSeverityCode,
                Comparator.nullsLast(Integer::compareTo)));

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

            String severityKey = String.valueOf(severity.getSeverityCode());

            SeverityYamlNode node = new SeverityYamlNode();
            node.setName(severity.getSeverityName());
            node.setDescription(severity.getDescription());
            exportData.getSeverities().put(severityKey, node);
        }
    }
}
