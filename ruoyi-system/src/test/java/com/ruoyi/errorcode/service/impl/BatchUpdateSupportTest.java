package com.ruoyi.errorcode.service.impl;

import java.util.List;
import org.junit.jupiter.api.Test;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.errorcode.domain.ErrorSystem;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BatchUpdateSupportTest
{
    @Test
    void shouldSortBatchById()
    {
        ErrorSystem second = system(2L);
        ErrorSystem first = system(1L);

        List<ErrorSystem> sorted = BatchUpdateSupport.prepare(
                List.of(second, first), ErrorSystem::getId);

        assertEquals(List.of(first, second), sorted);
    }

    @Test
    void shouldRejectDuplicateIds()
    {
        ServiceException exception = assertThrows(ServiceException.class,
                () -> BatchUpdateSupport.prepare(
                        List.of(system(1L), system(1L)), ErrorSystem::getId));

        assertEquals("批量修改数据存在重复ID：1", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyBatch()
    {
        ServiceException exception = assertThrows(ServiceException.class,
                () -> BatchUpdateSupport.prepare(List.of(), ErrorSystem::getId));

        assertEquals("批量修改数据不能为空", exception.getMessage());
    }

    private ErrorSystem system(Long id)
    {
        ErrorSystem system = new ErrorSystem();
        system.setId(id);
        return system;
    }
}
