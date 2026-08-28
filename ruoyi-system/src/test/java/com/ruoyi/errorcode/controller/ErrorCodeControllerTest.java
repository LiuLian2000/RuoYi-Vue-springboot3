package com.ruoyi.errorcode.controller;

import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import com.ruoyi.errorcode.domain.export.CategoryYamlNode;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlExport;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlNode;
import com.ruoyi.errorcode.domain.export.SeverityYamlNode;
import com.ruoyi.errorcode.domain.export.SystemYamlNode;
import com.ruoyi.errorcode.service.IErrorCodeYamlExportService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ErrorCodeControllerTest
{
    @Test
    void shouldWriteUtf8YamlAttachmentWithStringErrorCodeKeys() throws Exception
    {
        ErrorCodeYamlExport exportData = exportData();
        IErrorCodeYamlExportService exportService = mock(IErrorCodeYamlExportService.class);
        when(exportService.buildExportData()).thenReturn(exportData);

        ErrorCodeController controller = new ErrorCodeController();
        ReflectionTestUtils.setField(controller, "errorCodeYamlExportService", exportService);
        MockHttpServletResponse response = new MockHttpServletResponse();

        controller.exportYaml(response);

        String yaml = response.getContentAsString(StandardCharsets.UTF_8);
        assertEquals("application/x-yaml;charset=UTF-8", response.getContentType());
        assertTrue(response.getHeader("Content-Disposition").contains("error-codes.yml"));
        assertTrue(yaml.contains("severities:"));
        assertTrue(yaml.contains("\"3\":"));
        assertTrue(yaml.contains("\"1001\":"));
        assertTrue(yaml.contains("message: \"订单不存在\""));
        assertFalse(yaml.contains("enabled:"));
        assertFalse(yaml.startsWith("---"));
    }

    private ErrorCodeYamlExport exportData()
    {
        SeverityYamlNode severity = new SeverityYamlNode();
        severity.setName("错误");

        ErrorCodeYamlNode errorCode = new ErrorCodeYamlNode();
        errorCode.setMessage("订单不存在");
        errorCode.setSeverity(3);

        CategoryYamlNode category = new CategoryYamlNode();
        category.setName("支付类");
        category.getErrorCodes().put("1001", errorCode);

        SystemYamlNode system = new SystemYamlNode();
        system.setName("订单系统");
        system.getCategories().put("PAYMENT", category);

        ErrorCodeYamlExport exportData = new ErrorCodeYamlExport();
        exportData.getSeverities().put("3", severity);
        exportData.getSystems().put("ORDER", system);
        return exportData;
    }
}
