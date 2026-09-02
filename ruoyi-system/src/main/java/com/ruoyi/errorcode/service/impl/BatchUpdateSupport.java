package com.ruoyi.errorcode.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import com.ruoyi.common.exception.ServiceException;

/** 批量修改的通用参数校验及加锁顺序准备。 */
final class BatchUpdateSupport
{
    private static final int MAX_BATCH_SIZE = 100;

    private BatchUpdateSupport()
    {
    }

    static <T> List<T> prepare(List<T> items, Function<T, Long> idGetter)
    {
        if (items == null || items.isEmpty())
        {
            throw new ServiceException("批量修改数据不能为空");
        }
        if (items.size() > MAX_BATCH_SIZE)
        {
            throw new ServiceException("单次批量修改不能超过" + MAX_BATCH_SIZE + "条");
        }

        List<T> sortedItems = new ArrayList<>(items.size());
        Set<Long> ids = new HashSet<>();
        for (T item : items)
        {
            if (item == null)
            {
                throw new ServiceException("批量修改数据不能包含空项");
            }
            Long id = idGetter.apply(item);
            if (id == null)
            {
                throw new ServiceException("批量修改数据ID不能为空");
            }
            if (!ids.add(id))
            {
                throw new ServiceException("批量修改数据存在重复ID：" + id);
            }
            sortedItems.add(item);
        }
        // 排序防死锁
        sortedItems.sort(Comparator.comparing(idGetter));
        return sortedItems;
    }
}
