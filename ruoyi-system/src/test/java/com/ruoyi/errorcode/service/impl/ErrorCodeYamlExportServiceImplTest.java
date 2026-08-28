package com.ruoyi.errorcode.service.impl;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.domain.ErrorSystem;
import com.ruoyi.errorcode.domain.export.ErrorCodeYamlExport;
import com.ruoyi.errorcode.service.IErrorCategoryService;
import com.ruoyi.errorcode.service.IErrorCodeService;
import com.ruoyi.errorcode.service.IErrorSeverityService;
import com.ruoyi.errorcode.service.IErrorSystemService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErrorCodeYamlExportServiceImplTest
{
    @Mock
    private IErrorSystemService errorSystemService;

    @Mock
    private IErrorCategoryService errorCategoryService;

    @Mock
    private IErrorSeverityService errorSeverityService;

    @Mock
    private IErrorCodeService errorCodeService;

    private ErrorCodeYamlExportServiceImpl exportService;

    @BeforeEach
    void setUp()
    {
        exportService = new ErrorCodeYamlExportServiceImpl();
        ReflectionTestUtils.setField(exportService, "errorSystemService", errorSystemService);
        ReflectionTestUtils.setField(exportService, "errorCategoryService", errorCategoryService);
        ReflectionTestUtils.setField(exportService, "errorSeverityService", errorSeverityService);
        ReflectionTestUtils.setField(exportService, "errorCodeService", errorCodeService);
    }

    @Test
    void shouldExportOnlyCodesWhoseWholeReferenceChainIsEnabled()
    {
        when(errorSystemService.selectErrorSystemList(any())).thenReturn(List.of(
                system(1L, "ORDER", "订单系统", 1),
                system(2L, "PAYMENT", "支付系统", 1),
                system(3L, "DISABLED", "停用系统", 0)));
        when(errorCategoryService.selectErrorCategoryList(any())).thenReturn(List.of(
                category(11L, 1L, "PAYMENT", "支付类", 1),
                category(21L, 2L, "ACCOUNT", "账户类", 1),
                category(12L, 1L, "DISABLED", "停用类别", 0)));
        when(errorSeverityService.selectErrorSeverityList(any())).thenReturn(List.of(
                severity(2, "警告", 0),
                severity(3, "错误", 1)));
        when(errorCodeService.selectErrorCodeList(any())).thenReturn(List.of(
                errorCode(101L, 1L, 11L, "1001", 3, 1),
                errorCode(201L, 2L, 21L, "1001", 3, 1),
                errorCode(102L, 1L, 12L, "1002", 3, 1),
                errorCode(103L, 1L, 11L, "1003", 2, 1),
                errorCode(104L, 1L, 11L, "1004", 3, 0)));

        ErrorCodeYamlExport result = exportService.buildExportData();

        assertEquals(List.of("3"), List.copyOf(result.getSeverities().keySet()));
        assertEquals(List.of("ORDER", "PAYMENT"), List.copyOf(result.getSystems().keySet()));
        assertTrue(result.getSystems().get("ORDER").getCategories().get("PAYMENT")
                .getErrorCodes().containsKey("1001"));
        assertTrue(result.getSystems().get("PAYMENT").getCategories().get("ACCOUNT")
                .getErrorCodes().containsKey("1001"));
        assertFalse(result.getSystems().get("ORDER").getCategories().get("PAYMENT")
                .getErrorCodes().containsKey("1003"));
    }

    private ErrorSystem system(Long id, String code, String name, Integer status)
    {
        ErrorSystem value = new ErrorSystem();
        value.setId(id);
        value.setSystemCode(code);
        value.setSystemName(name);
        value.setStatus(status);
        return value;
    }

    private ErrorCategory category(Long id, Long systemId, String code, String name, Integer status)
    {
        ErrorCategory value = new ErrorCategory();
        value.setId(id);
        value.setSystemId(systemId);
        value.setCategoryCode(code);
        value.setCategoryName(name);
        value.setStatus(status);
        return value;
    }

    private ErrorSeverity severity(Integer code, String name, Integer status)
    {
        ErrorSeverity value = new ErrorSeverity();
        value.setSeverityCode(code);
        value.setSeverityName(name);
        value.setStatus(status);
        return value;
    }

    private ErrorCode errorCode(Long id, Long systemId, Long categoryId, String code,
            Integer severity, Integer status)
    {
        ErrorCode value = new ErrorCode();
        value.setId(id);
        value.setSystemId(systemId);
        value.setCategoryId(categoryId);
        value.setCode(code);
        value.setMessage("错误 " + code);
        value.setSeverity(severity);
        value.setStatus(status);
        return value;
    }
}
