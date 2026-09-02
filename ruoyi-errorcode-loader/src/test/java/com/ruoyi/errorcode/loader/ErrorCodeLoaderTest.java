package com.ruoyi.errorcode.loader;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ErrorCodeLoaderTest
{
    private final ErrorCodeLoader loader = new ErrorCodeLoader();

    @Test
    void shouldLoadAllInformationAndSupportCombinedQueries()
    {
        ErrorCodeRegistry registry = load("""
                severities:
                  "2":
                    name: "警告"
                    description: "需要关注"
                  "3":
                    name: "错误"
                    description: "业务失败"
                systems:
                  ORDER:
                    name: "订单系统"
                    description: "订单相关业务"
                    categories:
                      CREATE_ORDER:
                        name: "订单创建"
                        description: "订单创建流程"
                        errorCodes:
                          "1001":
                            message: "订单不存在"
                            description: "未查询到指定订单"
                            severity: 3
                  PAYMENT:
                    name: "支付系统"
                    description: "支付相关业务"
                    categories:
                      PAY_CHANNEL:
                        name: "支付渠道"
                        description: "第三方支付渠道"
                        errorCodes:
                          "1001":
                            message: "支付渠道不可用"
                            description: "渠道响应超时"
                            severity: 2
                """);

        assertEquals(2, registry.getAll().size());

        ErrorCodeDefinition orderError = registry
                .requireBySystemCodeAndErrorCode("ORDER", "1001");
        assertEquals("订单系统", orderError.systemName());
        assertEquals("订单创建", orderError.categoryName());
        assertEquals("订单不存在", orderError.message());
        assertEquals("错误", orderError.severityName());
        assertEquals("业务失败", orderError.severityDescription());

        assertEquals(2, registry.findByErrorCode("1001").size());
        assertEquals(List.of(orderError),
                registry.findByNames("订单系统", "订单创建", "1001"));
        assertEquals(List.of(orderError), registry.query(ErrorCodeQuery.create()
                .systemCode("ORDER")
                .categoryCode("CREATE_ORDER")
                .severityCode(3)
                .keyword("指定订单")));
    }

    @Test
    void shouldRejectMissingSeverityReference()
    {
        ErrorCodeLoadException exception = assertThrows(ErrorCodeLoadException.class,
                () -> load("""
                        severities: {}
                        systems:
                          ORDER:
                            name: "订单系统"
                            categories:
                              CREATE_ORDER:
                                name: "订单创建"
                                errorCodes:
                                  "1001":
                                    message: "订单不存在"
                                    severity: 3
                        """));

        assertTrue(exception.getMessage().contains("不存在的严重程度"));
    }

    @Test
    void shouldRejectDuplicateErrorCodeWithinOneSystem()
    {
        ErrorCodeLoadException exception = assertThrows(ErrorCodeLoadException.class,
                () -> load("""
                        severities:
                          "3":
                            name: "错误"
                        systems:
                          ORDER:
                            name: "订单系统"
                            categories:
                              CREATE_ORDER:
                                name: "订单创建"
                                errorCodes:
                                  "1001":
                                    message: "订单不存在"
                                    severity: 3
                              FULFILLMENT:
                                name: "订单履约"
                                errorCodes:
                                  "1001":
                                    message: "订单状态错误"
                                    severity: 3
                        """));

        assertTrue(exception.getMessage().contains("重复错误码"));
    }

    private ErrorCodeRegistry load(String yaml)
    {
        return loader.load(new ByteArrayInputStream(yaml.getBytes(StandardCharsets.UTF_8)));
    }
}
