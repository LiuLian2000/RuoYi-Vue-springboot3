package com.ruoyi.errorcode.service;

import com.ruoyi.errorcode.domain.export.ErrorCodeYamlExport;

/**
 * 错误码 YAML 导出服务。
 */
public interface IErrorCodeYamlExportService
{
    /**
     * 构建仅包含有效启用数据的 YAML 导出结构。
     *
     * @return YAML 导出根节点
     */
    ErrorCodeYamlExport buildExportData();
}
