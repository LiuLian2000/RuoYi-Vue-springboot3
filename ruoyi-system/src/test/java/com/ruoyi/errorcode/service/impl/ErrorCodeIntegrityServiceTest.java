package com.ruoyi.errorcode.service.impl;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.domain.ErrorCategory;
import com.ruoyi.errorcode.domain.ErrorCode;
import com.ruoyi.errorcode.domain.ErrorSeverity;
import com.ruoyi.errorcode.domain.ErrorSystem;
import com.ruoyi.errorcode.mapper.ErrorCategoryMapper;
import com.ruoyi.errorcode.mapper.ErrorCodeMapper;
import com.ruoyi.errorcode.mapper.ErrorSeverityMapper;
import com.ruoyi.errorcode.mapper.ErrorSystemMapper;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;

@ExtendWith(MockitoExtension.class)
class ErrorCodeIntegrityServiceTest
{
    @Mock
    private ErrorSystemMapper errorSystemMapper;

    @Mock
    private ErrorCategoryMapper errorCategoryMapper;

    @Mock
    private ErrorCodeMapper errorCodeMapper;

    @Mock
    private ErrorSeverityMapper errorSeverityMapper;

    @InjectMocks
    private ErrorSystemServiceImpl errorSystemService;

    @InjectMocks
    private ErrorCategoryServiceImpl errorCategoryService;

    @InjectMocks
    private ErrorSeverityServiceImpl errorSeverityService;

    @InjectMocks
    private ErrorCodeServiceImpl errorCodeService;

    @Test
    void shouldRejectChangingCategorySystem()
    {
        ErrorCategory update = new ErrorCategory();
        update.setId(10L);
        update.setSystemId(2L);

        assertThrows(ServiceException.class, () -> errorCategoryService.updateErrorCategory(update));
        verify(errorCategoryMapper, never()).updateErrorCategory(update);
    }

    @Test
    void shouldRejectDeletingSystemThatStillHasCategories()
    {
        Long[] ids = { 1L };
        when(errorSystemMapper.selectErrorSystemsByIdsForUpdate(ids))
                .thenReturn(java.util.List.of(new ErrorSystem()));
        when(errorCategoryMapper.countErrorCategoriesBySystemIds(ids)).thenReturn(1);

        assertThrows(ServiceException.class, () -> errorSystemService.deleteErrorSystemByIds(ids));
        verify(errorSystemMapper, never()).deleteErrorSystemByIds(ids);
    }

    @Test
    void shouldRejectErrorCodeWhoseCategoryBelongsToAnotherSystem()
    {
        ErrorSystem system = new ErrorSystem();
        system.setId(1L);
        system.setStatus(1);
        when(errorSystemMapper.selectErrorSystemsByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(system));

        ErrorCategory category = new ErrorCategory();
        category.setId(10L);
        category.setSystemId(2L);
        category.setStatus(1);
        when(errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(category));

        ErrorCode errorCode = new ErrorCode();
        errorCode.setSystemId(1L);
        errorCode.setCategoryId(10L);
        errorCode.setSeverityId(3L);
        errorCode.setStatus(1);

        assertThrows(ServiceException.class, () -> errorCodeService.insertErrorCode(errorCode));
        verify(errorCodeMapper, never()).insertErrorCode(errorCode);
    }

    @Test
    void shouldLockParentsInOrderBeforeInsertingErrorCode()
    {
        ErrorSystem system = new ErrorSystem();
        system.setId(1L);
        system.setStatus(1);
        when(errorSystemMapper.selectErrorSystemsByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(system));

        ErrorCategory category = new ErrorCategory();
        category.setId(10L);
        category.setSystemId(1L);
        category.setStatus(1);
        when(errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(category));

        ErrorSeverity severity = new ErrorSeverity();
        severity.setId(3L);
        severity.setSeverityCode(3);
        severity.setStatus(1);
        when(errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(severity));

        ErrorCode errorCode = new ErrorCode();
        errorCode.setSystemId(1L);
        errorCode.setCategoryId(10L);
        errorCode.setSeverityId(3L);
        errorCode.setStatus(1);
        when(errorCodeMapper.insertErrorCode(errorCode)).thenReturn(1);

        assertEquals(1, errorCodeService.insertErrorCode(errorCode));

        InOrder order = inOrder(errorSystemMapper, errorCategoryMapper,
                errorSeverityMapper, errorCodeMapper);
        order.verify(errorSystemMapper).selectErrorSystemsByIdsForUpdate(any(Long[].class));
        order.verify(errorCategoryMapper).selectErrorCategoriesByIdsForUpdate(any(Long[].class));
        order.verify(errorSeverityMapper).selectErrorSeveritiesByIdsForUpdate(any(Long[].class));
        order.verify(errorCodeMapper).insertErrorCode(errorCode);
    }

    @Test
    void shouldRejectSystemUpdateWithoutBusinessFields()
    {
        ErrorSystem update = new ErrorSystem();
        update.setId(1L);
        update.setUpdateBy("admin");

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorSystemService.updateErrorSystem(update));

        assertEquals("没有需要修改的内容", exception.getMessage());
        verify(errorSystemMapper, never()).updateErrorSystem(update);
    }

    @Test
    void shouldRejectCategoryUpdateWithoutBusinessFields()
    {
        ErrorCategory update = new ErrorCategory();
        update.setId(10L);
        update.setUpdateBy("admin");

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorCategoryService.updateErrorCategory(update));

        assertEquals("没有需要修改的内容", exception.getMessage());
        verify(errorCategoryMapper, never()).updateErrorCategory(update);
    }

    @Test
    void shouldRejectSeverityUpdateWithoutBusinessFields()
    {
        ErrorSeverity update = new ErrorSeverity();
        update.setId(3L);
        update.setUpdateBy("admin");

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorSeverityService.updateErrorSeverity(update));

        assertEquals("没有需要修改的内容", exception.getMessage());
        verify(errorSeverityMapper, never()).updateErrorSeverity(update);
    }

    @Test
    void shouldRejectErrorCodeUpdateWithoutBusinessFields()
    {
        ErrorCode existing = new ErrorCode();
        existing.setId(100L);
        when(errorCodeMapper.selectErrorCodeById(100L)).thenReturn(existing);

        ErrorCode update = new ErrorCode();
        update.setId(100L);
        update.setUpdateBy("admin");

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorCodeService.updateErrorCode(update));

        assertEquals("没有需要修改的内容", exception.getMessage());
        verify(errorCodeMapper, never()).updateErrorCode(update);
    }

    @Test
    void shouldRejectStaleSystemUpdate()
    {
        ErrorSystem update = new ErrorSystem();
        update.setId(1L);
        update.setSystemName("订单系统");
        update.setVersion(2);
        when(errorSystemMapper.updateErrorSystem(update)).thenReturn(0);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorSystemService.updateErrorSystem(update));

        assertEquals("数据不存在、已删除或已被其他用户修改，请刷新后重试", exception.getMessage());
    }

    @Test
    void shouldRejectStaleCategoryUpdate()
    {
        ErrorCategory existing = new ErrorCategory();
        existing.setId(10L);
        existing.setSystemId(1L);
        existing.setVersion(3);
        when(errorCategoryMapper.selectErrorCategoryById(10L)).thenReturn(existing);

        ErrorCategory update = new ErrorCategory();
        update.setId(10L);
        update.setCategoryName("支付错误");
        update.setVersion(2);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorCategoryService.updateErrorCategory(update));

        assertEquals("数据已被其他用户修改，请刷新后重试", exception.getMessage());
        verify(errorCategoryMapper, never()).updateErrorCategory(update);
    }

    @Test
    void shouldRejectStaleSeverityUpdate()
    {
        ErrorSeverity existing = new ErrorSeverity();
        existing.setId(3L);
        existing.setSeverityCode(2);
        existing.setStatus(1);
        existing.setVersion(4);
        when(errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(any(Long[].class)))
                .thenReturn(List.of(existing));

        ErrorSeverity update = new ErrorSeverity();
        update.setId(3L);
        update.setSeverityCode(2);
        update.setSeverityName("错误");
        update.setStatus(1);
        update.setVersion(3);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorSeverityService.updateErrorSeverity(update));

        assertEquals("数据已被其他用户修改，请刷新后重试", exception.getMessage());
        verify(errorSeverityMapper, never()).updateErrorSeverity(update);
    }

    @Test
    void shouldRejectStaleErrorCodeUpdate()
    {
        ErrorCode existing = new ErrorCode();
        existing.setId(100L);
        existing.setVersion(6);
        when(errorCodeMapper.selectErrorCodeById(100L)).thenReturn(existing);

        ErrorCode update = new ErrorCode();
        update.setId(100L);
        update.setMessage("订单不存在");
        update.setVersion(5);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorCodeService.updateErrorCode(update));

        assertEquals("数据已被其他用户修改，请刷新后重试", exception.getMessage());
        verify(errorCodeMapper, never()).updateErrorCode(update);
    }

    @Test
    void shouldRejectDisablingSystemWithEnabledCategories()
    {
        ErrorSystem existing = new ErrorSystem();
        existing.setId(1L);
        existing.setStatus(1);
        existing.setVersion(2);
        when(errorSystemMapper.selectErrorSystemsByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(existing));
        when(errorCategoryMapper.countEnabledErrorCategoriesBySystemIds(any(Long[].class)))
                .thenReturn(1);

        ErrorSystem update = new ErrorSystem();
        update.setId(1L);
        update.setStatus(0);
        update.setVersion(2);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorSystemService.updateErrorSystem(update));

        assertEquals("系统下存在启用的类别，请先停用相关类别", exception.getMessage());
        verify(errorSystemMapper, never()).updateErrorSystem(update);
    }

    @Test
    void shouldRejectDisablingCategoryWithEnabledErrorCodes()
    {
        ErrorCategory existing = new ErrorCategory();
        existing.setId(10L);
        existing.setSystemId(1L);
        existing.setStatus(1);
        existing.setVersion(3);
        when(errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(existing));
        when(errorCodeMapper.countEnabledErrorCodesByCategoryIds(any(Long[].class)))
                .thenReturn(1);

        ErrorCategory update = new ErrorCategory();
        update.setId(10L);
        update.setStatus(0);
        update.setVersion(3);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorCategoryService.updateErrorCategory(update));

        assertEquals("类别下存在启用的错误码，请先停用相关错误码", exception.getMessage());
        verify(errorCategoryMapper, never()).updateErrorCategory(update);
    }

    @Test
    void shouldRejectDisablingSeverityReferencedByEnabledErrorCodes()
    {
        ErrorSeverity existing = new ErrorSeverity();
        existing.setId(3L);
        existing.setSeverityCode(3);
        existing.setStatus(1);
        existing.setVersion(4);
        when(errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(existing));
        when(errorCodeMapper.countEnabledErrorCodesBySeverityIds(any(Long[].class)))
                .thenReturn(1);

        ErrorSeverity update = new ErrorSeverity();
        update.setId(3L);
        update.setSeverityCode(3);
        update.setStatus(0);
        update.setVersion(4);

        ServiceException exception = assertThrows(ServiceException.class,
                () -> errorSeverityService.updateErrorSeverity(update));

        assertEquals("严重程度已被启用的错误码引用，请先停用相关错误码", exception.getMessage());
        verify(errorSeverityMapper, never()).updateErrorSeverity(update);
    }

    @Test
    void shouldAllowChangingSeverityCodeWithoutCheckingErrorCodeReferences()
    {
        ErrorSeverity existing = new ErrorSeverity();
        existing.setId(3L);
        existing.setSeverityCode(3);
        existing.setStatus(1);
        existing.setVersion(4);
        when(errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(any(Long[].class)))
                .thenReturn(List.of(existing));

        ErrorSeverity update = new ErrorSeverity();
        update.setId(3L);
        update.setSeverityCode(4);
        update.setSeverityName("严重错误");
        update.setStatus(1);
        update.setVersion(4);
        when(errorSeverityMapper.updateErrorSeverity(update)).thenReturn(1);

        assertEquals(1, errorSeverityService.updateErrorSeverity(update));

        verify(errorCodeMapper, never()).countErrorCodesBySeverityIds(any(Long[].class));
        verify(errorCodeMapper, never()).countEnabledErrorCodesBySeverityIds(any(Long[].class));
    }

    @Test
    void shouldUpdateBatchInIdOrder()
    {
        when(errorSystemMapper.updateErrorSystem(any(ErrorSystem.class))).thenReturn(1);

        ErrorSystem second = new ErrorSystem();
        second.setId(2L);
        second.setSystemName("支付系统");
        second.setVersion(0);
        ErrorSystem first = new ErrorSystem();
        first.setId(1L);
        first.setSystemName("订单系统");
        first.setVersion(0);

        assertEquals(2, errorSystemService.updateErrorSystemBatch(
                java.util.List.of(second, first)));

        ArgumentCaptor<ErrorSystem> captor = ArgumentCaptor.forClass(ErrorSystem.class);
        verify(errorSystemMapper, times(2)).updateErrorSystem(captor.capture());
        assertEquals(java.util.List.of(1L, 2L), captor.getAllValues().stream()
                .map(ErrorSystem::getId).toList());
    }

    @Test
    void shouldUseOnlyOptimisticLockForOrdinarySystemUpdate()
    {
        ErrorSystem update = new ErrorSystem();
        update.setId(1L);
        update.setSystemName("订单中心");
        update.setVersion(2);
        when(errorSystemMapper.updateErrorSystem(update)).thenReturn(1);

        assertEquals(1, errorSystemService.updateErrorSystem(update));

        verify(errorSystemMapper, never()).selectErrorSystemById(1L);
        verify(errorSystemMapper, never()).selectErrorSystemsByIdsForUpdate(any(Long[].class));
    }

    @Test
    void shouldUseOnlyOptimisticLockForOrdinaryCategoryUpdate()
    {
        ErrorCategory existing = new ErrorCategory();
        existing.setId(10L);
        existing.setSystemId(1L);
        existing.setStatus(1);
        existing.setVersion(2);
        when(errorCategoryMapper.selectErrorCategoryById(10L)).thenReturn(existing);

        ErrorCategory update = new ErrorCategory();
        update.setId(10L);
        update.setCategoryName("订单创建");
        update.setVersion(2);
        when(errorCategoryMapper.updateErrorCategory(update)).thenReturn(1);

        assertEquals(1, errorCategoryService.updateErrorCategory(update));

        verify(errorSystemMapper, never()).selectErrorSystemsByIdsForUpdate(any(Long[].class));
        verify(errorCategoryMapper, never()).selectErrorCategoriesByIdsForUpdate(any(Long[].class));
    }

    @Test
    void shouldSkipReferenceQueriesForOrdinarySeverityUpdate()
    {
        ErrorSeverity existing = new ErrorSeverity();
        existing.setId(3L);
        existing.setSeverityCode(2);
        existing.setStatus(1);
        existing.setVersion(2);
        when(errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(any(Long[].class)))
                .thenReturn(List.of(existing));

        ErrorSeverity update = new ErrorSeverity();
        update.setId(3L);
        update.setSeverityCode(2);
        update.setSeverityName("业务错误");
        update.setStatus(1);
        update.setVersion(2);
        when(errorSeverityMapper.updateErrorSeverity(update)).thenReturn(1);

        assertEquals(1, errorSeverityService.updateErrorSeverity(update));

        verify(errorSeverityMapper).selectErrorSeveritiesByIdsForUpdate(any(Long[].class));
        verify(errorCodeMapper, never()).countErrorCodesBySeverityIds(any(Long[].class));
        verify(errorCodeMapper, never()).countEnabledErrorCodesBySeverityIds(any(Long[].class));
    }

    @Test
    void shouldUseOnlyOptimisticLockForOrdinaryErrorCodeUpdate()
    {
        ErrorCode existing = new ErrorCode();
        existing.setId(100L);
        existing.setSystemId(1L);
        existing.setCategoryId(10L);
        existing.setSeverityId(3L);
        existing.setStatus(1);
        existing.setVersion(2);
        when(errorCodeMapper.selectErrorCodeById(100L)).thenReturn(existing);

        ErrorCode update = new ErrorCode();
        update.setId(100L);
        update.setMessage("订单不存在");
        update.setVersion(2);
        when(errorCodeMapper.updateErrorCode(update)).thenReturn(1);

        assertEquals(1, errorCodeService.updateErrorCode(update));

        verify(errorSystemMapper, never()).selectErrorSystemsByIdsForUpdate(any(Long[].class));
        verify(errorCategoryMapper, never()).selectErrorCategoriesByIdsForUpdate(any(Long[].class));
        verify(errorSeverityMapper, never()).selectErrorSeveritiesByIdsForUpdate(any(Long[].class));
    }

    @Test
    void shouldLockNewParentsWhenChangingErrorCodeAssociations()
    {
        ErrorCode existing = new ErrorCode();
        existing.setId(100L);
        existing.setSystemId(1L);
        existing.setCategoryId(10L);
        existing.setSeverityId(3L);
        existing.setStatus(1);
        existing.setVersion(2);
        when(errorCodeMapper.selectErrorCodeById(100L)).thenReturn(existing);

        ErrorSystem system = new ErrorSystem();
        system.setId(2L);
        system.setStatus(1);
        when(errorSystemMapper.selectErrorSystemsByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(system));
        ErrorCategory category = new ErrorCategory();
        category.setId(20L);
        category.setSystemId(2L);
        category.setStatus(1);
        when(errorCategoryMapper.selectErrorCategoriesByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(category));
        ErrorSeverity severity = new ErrorSeverity();
        severity.setId(4L);
        severity.setSeverityCode(4);
        severity.setStatus(1);
        when(errorSeverityMapper.selectErrorSeveritiesByIdsForUpdate(any(Long[].class)))
                .thenReturn(java.util.List.of(severity));

        ErrorCode update = new ErrorCode();
        update.setId(100L);
        update.setSystemId(2L);
        update.setCategoryId(20L);
        update.setSeverityId(4L);
        update.setVersion(2);
        when(errorCodeMapper.updateErrorCode(update)).thenReturn(1);

        assertEquals(1, errorCodeService.updateErrorCode(update));

        verify(errorSystemMapper).selectErrorSystemsByIdsForUpdate(any(Long[].class));
        verify(errorCategoryMapper).selectErrorCategoriesByIdsForUpdate(any(Long[].class));
        verify(errorSeverityMapper).selectErrorSeveritiesByIdsForUpdate(any(Long[].class));
    }
}
