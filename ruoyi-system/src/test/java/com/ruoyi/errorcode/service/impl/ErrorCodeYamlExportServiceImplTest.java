package com.ruoyi.errorcode.service.impl;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlExport;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.service.IErrorSeverityService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErrorCodeYamlExportServiceImplTest
{
    @Mock
    private IErrorSeverityService errorSeverityService;

    @Mock
    private ErrorCodeMapper errorCodeMapper;

    private ErrorCodeYamlExportServiceImpl exportService;

    @BeforeEach
    void setUp()
    {
        exportService = new ErrorCodeYamlExportServiceImpl();
        ReflectionTestUtils.setField(exportService, "errorSeverityService", errorSeverityService);
        ReflectionTestUtils.setField(exportService, "errorCodeMapper", errorCodeMapper);
    }

    @Test
    void shouldBuildTreeFromEffectiveExportRows()
    {
        when(errorSeverityService.selectErrorSeverityList(any())).thenReturn(List.of(
                severity(2, "警告", 0),
                severity(3, "错误", 1)));
        when(errorCodeMapper.selectEnabledErrorCodeExportList()).thenReturn(List.of(
                errorCode("ORDER", "订单系统", "PAYMENT", "支付类", "1001", 3),
                errorCode("PAYMENT", "支付系统", "ACCOUNT", "账户类", "1001", 3)));

        ErrorCodeYamlExport result = exportService.buildExportData();

        assertEquals(List.of("3"), List.copyOf(result.getSeverities().keySet()));
        assertFalse(result.getSeverities().containsKey("2"));
        assertEquals(List.of("ORDER", "PAYMENT"), List.copyOf(result.getSystems().keySet()));
        assertTrue(result.getSystems().get("ORDER").getCategories().get("PAYMENT")
                .getErrorCodes().containsKey("1001"));
        assertTrue(result.getSystems().get("PAYMENT").getCategories().get("ACCOUNT")
                .getErrorCodes().containsKey("1001"));
    }

    private ErrorSeverity severity(Integer code, String name, Integer status)
    {
        ErrorSeverity value = new ErrorSeverity();
        value.setSeverityCode(code);
        value.setSeverityName(name);
        value.setStatus(status);
        return value;
    }

    private ErrorCode errorCode(String systemCode, String systemName, String categoryCode,
            String categoryName, String code, Integer severityCode)
    {
        ErrorCode value = new ErrorCode();
        value.setSystemCode(systemCode);
        value.setSystemName(systemName);
        value.setCategoryCode(categoryCode);
        value.setCategoryName(categoryName);
        value.setCode(code);
        value.setMessage("错误 " + code);
        value.setSeverityCode(severityCode);
        return value;
    }
}
