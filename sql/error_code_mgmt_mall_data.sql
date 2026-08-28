-- ============================================================
-- 错误码管理示例数据
-- 前置条件：先执行 error_code_mgmt_mall.sql 创建表结构
-- 说明：脚本按有效数据（del_flag = 0）的业务唯一键去重，可重复执行
-- ============================================================

SET NAMES utf8mb4;
USE `ry-vue`;

START TRANSACTION;

-- 1. 严重程度
INSERT INTO error_severity
    (severity_code, severity_name, description, status)
SELECT sample.severity_code, sample.severity_name, sample.description, 1
FROM (
    SELECT 1 AS severity_code, '提示' AS severity_name, '提示性信息，不影响主要业务流程' AS description
    UNION ALL SELECT 2, '警告', '业务仍可继续，但需要关注或人工确认'
    UNION ALL SELECT 3, '错误', '当前业务操作失败，需要修正后重试'
    UNION ALL SELECT 4, '致命', '核心服务不可用，需要立即处理'
) AS sample
LEFT JOIN error_severity existing
    ON existing.severity_code = sample.severity_code
   AND existing.del_flag = 0
WHERE existing.id IS NULL;

-- 2. 所属系统
INSERT INTO error_system
    (system_code, system_name, description, status)
SELECT sample.system_code, sample.system_name, sample.description, 1
FROM (
    SELECT 'MALL' AS system_code, '商城中心' AS system_name, '商品、会员和购物车相关业务' AS description
    UNION ALL SELECT 'ORDER', '订单中心', '订单创建、履约和售后相关业务'
    UNION ALL SELECT 'PAYMENT', '支付中心', '支付、退款和对账相关业务'
) AS sample
LEFT JOIN error_system existing
    ON existing.system_code = sample.system_code
   AND existing.del_flag = 0
WHERE existing.id IS NULL;

-- 3. 错误分类
INSERT INTO error_category
    (system_id, category_code, category_name, description, status)
SELECT system_data.id, sample.category_code, sample.category_name, sample.description, 1
FROM (
    SELECT 'MALL' AS system_code, 'PRODUCT' AS category_code, '商品管理' AS category_name, '商品查询、上下架及库存展示' AS description
    UNION ALL SELECT 'MALL', 'MEMBER', '会员管理', '会员资料、等级和状态管理'
    UNION ALL SELECT 'MALL', 'CART', '购物车', '购物车商品维护及价格计算'
    UNION ALL SELECT 'ORDER', 'CREATE_ORDER', '订单创建', '订单校验与创建流程'
    UNION ALL SELECT 'ORDER', 'FULFILLMENT', '订单履约', '发货、收货及物流流程'
    UNION ALL SELECT 'PAYMENT', 'PAY_CHANNEL', '支付渠道', '第三方支付渠道调用'
    UNION ALL SELECT 'PAYMENT', 'REFUND', '退款管理', '退款申请和退款处理'
) AS sample
JOIN error_system system_data
    ON system_data.system_code = sample.system_code
   AND system_data.del_flag = 0
LEFT JOIN error_category existing
    ON existing.system_id = system_data.id
   AND existing.category_code = sample.category_code
   AND existing.del_flag = 0
WHERE existing.id IS NULL;

-- 4. 错误码
INSERT INTO error_code
    (system_id, category_id, code, message, description, severity, status)
SELECT system_data.id,
       category_data.id,
       sample.code,
       sample.message,
       sample.description,
       sample.severity,
       sample.status
FROM (
    SELECT 'MALL' AS system_code, 'PRODUCT' AS category_code, 'MALL-1001' AS code,
           '商品不存在' AS message, '根据商品 ID 未查询到有效商品' AS description, 3 AS severity, 1 AS status
    UNION ALL SELECT 'MALL', 'PRODUCT', 'MALL-1002', '商品已下架', '商品当前不可购买，请重新选择商品', 2, 1
    UNION ALL SELECT 'MALL', 'MEMBER', 'MALL-1101', '会员状态异常', '会员账号已被冻结或注销', 3, 1
    UNION ALL SELECT 'MALL', 'CART', 'MALL-1201', '购物车商品数量超限', '单个商品加入购物车的数量超过允许上限', 2, 1
    UNION ALL SELECT 'ORDER', 'CREATE_ORDER', 'ORDER-2001', '订单创建失败', '订单数据校验失败，未能生成订单', 3, 1
    UNION ALL SELECT 'ORDER', 'CREATE_ORDER', 'ORDER-2002', '订单重复提交', '同一幂等键已创建订单，请勿重复提交', 2, 1
    UNION ALL SELECT 'ORDER', 'FULFILLMENT', 'ORDER-2101', '订单当前状态不允许发货', '只有待发货状态的订单可以执行发货操作', 3, 1
    UNION ALL SELECT 'PAYMENT', 'PAY_CHANNEL', 'PAY-3001', '支付渠道暂不可用', '第三方支付渠道响应超时或服务异常', 4, 1
    UNION ALL SELECT 'PAYMENT', 'PAY_CHANNEL', 'PAY-3002', '支付金额不一致', '支付回调金额与订单应付金额不一致', 4, 1
    UNION ALL SELECT 'PAYMENT', 'REFUND', 'PAY-3101', '退款金额超过可退金额', '申请退款金额不能超过订单剩余可退金额', 3, 1
    UNION ALL SELECT 'PAYMENT', 'REFUND', 'PAY-3102', '退款处理中', '退款申请已受理，请勿重复提交', 1, 0
) AS sample
JOIN error_system system_data
    ON system_data.system_code = sample.system_code
   AND system_data.del_flag = 0
JOIN error_category category_data
    ON category_data.system_id = system_data.id
   AND category_data.category_code = sample.category_code
   AND category_data.del_flag = 0
LEFT JOIN error_code existing
    ON existing.system_id = system_data.id
   AND existing.code = sample.code
   AND existing.del_flag = 0
WHERE existing.id IS NULL;

-- 5. 示例变更日志：为 MALL-1001 添加一条创建日志和一条修改日志
SET @sample_error_code_id := (
    SELECT error_data.id
    FROM error_code error_data
    JOIN error_system system_data ON system_data.id = error_data.system_id
    WHERE system_data.system_code = 'MALL'
      AND system_data.del_flag = 0
      AND error_data.code = 'MALL-1001'
      AND error_data.del_flag = 0
    LIMIT 1
);

INSERT INTO error_code_log
    (error_code_id, operation, operator, remark, create_time)
SELECT @sample_error_code_id, 'CREATE', 'demo_admin', '初始化示例错误码', '2026-08-01 09:00:00'
WHERE @sample_error_code_id IS NOT NULL
  AND NOT EXISTS (
      SELECT 1
      FROM error_code_log
      WHERE error_code_id = @sample_error_code_id
        AND operation = 'CREATE'
        AND operator = 'demo_admin'
        AND remark = '初始化示例错误码'
        AND del_flag = 0
  );

SET @sample_create_log_id := (
    SELECT id
    FROM error_code_log
    WHERE error_code_id = @sample_error_code_id
      AND operation = 'CREATE'
      AND operator = 'demo_admin'
      AND remark = '初始化示例错误码'
      AND del_flag = 0
    ORDER BY id
    LIMIT 1
);

INSERT INTO error_code_log_detail (log_id, field_name, old_value, new_value)
SELECT @sample_create_log_id, sample.field_name, NULL, sample.new_value
FROM (
    SELECT 'code' AS field_name, 'MALL-1001' AS new_value
    UNION ALL SELECT 'message', '商品不存在'
    UNION ALL SELECT 'severity', '3'
) AS sample
WHERE @sample_create_log_id IS NOT NULL
  AND NOT EXISTS (
      SELECT 1
      FROM error_code_log_detail detail
      WHERE detail.log_id = @sample_create_log_id
        AND detail.field_name = sample.field_name
        AND detail.del_flag = 0
  );

INSERT INTO error_code_log
    (error_code_id, operation, operator, remark, create_time)
SELECT @sample_error_code_id, 'UPDATE', 'demo_admin', '补充错误提示说明', '2026-08-02 14:30:00'
WHERE @sample_error_code_id IS NOT NULL
  AND NOT EXISTS (
      SELECT 1
      FROM error_code_log
      WHERE error_code_id = @sample_error_code_id
        AND operation = 'UPDATE'
        AND operator = 'demo_admin'
        AND remark = '补充错误提示说明'
        AND del_flag = 0
  );

SET @sample_update_log_id := (
    SELECT id
    FROM error_code_log
    WHERE error_code_id = @sample_error_code_id
      AND operation = 'UPDATE'
      AND operator = 'demo_admin'
      AND remark = '补充错误提示说明'
      AND del_flag = 0
    ORDER BY id
    LIMIT 1
);

INSERT INTO error_code_log_detail (log_id, field_name, old_value, new_value)
SELECT @sample_update_log_id,
       'description',
       '未查询到商品',
       '根据商品 ID 未查询到有效商品'
WHERE @sample_update_log_id IS NOT NULL
  AND NOT EXISTS (
      SELECT 1
      FROM error_code_log_detail
      WHERE log_id = @sample_update_log_id
        AND field_name = 'description'
        AND del_flag = 0
  );

COMMIT;

-- 执行后可用以下语句快速检查数据量
SELECT 'error_system' AS table_name, COUNT(*) AS active_count FROM error_system WHERE del_flag = 0
UNION ALL SELECT 'error_category', COUNT(*) FROM error_category WHERE del_flag = 0
UNION ALL SELECT 'error_severity', COUNT(*) FROM error_severity WHERE del_flag = 0
UNION ALL SELECT 'error_code', COUNT(*) FROM error_code WHERE del_flag = 0
UNION ALL SELECT 'error_code_log', COUNT(*) FROM error_code_log WHERE del_flag = 0
UNION ALL SELECT 'error_code_log_detail', COUNT(*) FROM error_code_log_detail WHERE del_flag = 0;
