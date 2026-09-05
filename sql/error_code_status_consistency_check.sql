-- 结果为空表示状态层级一致。本脚本只查询，不修改数据。

-- 启用类别的所属系统必须启用且未删除。
SELECT ec.id, ec.system_id, ec.category_code, ec.category_name,
       es.status AS system_status, es.del_flag AS system_del_flag
FROM error_category ec
LEFT JOIN error_system es ON es.id = ec.system_id
WHERE ec.status = 1
  AND ec.del_flag = 0
  AND (es.id IS NULL OR es.status <> 1 OR es.del_flag <> 0);

-- 启用错误码的系统、类别、严重程度必须全部启用，且类别必须属于该系统。
SELECT e.id, e.code, e.system_id, e.category_id, e.severity_id,
       ev.severity_code,
       es.status AS system_status,
       ec.status AS category_status,
       ev.status AS severity_status
FROM error_code e
LEFT JOIN error_system es
       ON es.id = e.system_id AND es.del_flag = 0
LEFT JOIN error_category ec
       ON ec.id = e.category_id AND ec.del_flag = 0
LEFT JOIN error_severity ev
       ON ev.id = e.severity_id AND ev.del_flag = 0
WHERE e.status = 1
  AND e.del_flag = 0
  AND (es.id IS NULL OR es.status <> 1
       OR ec.id IS NULL OR ec.status <> 1 OR ec.system_id <> e.system_id
       OR ev.id IS NULL OR ev.status <> 1);
