# 错误码 YAML 加载器

该模块读取错误码管理系统导出的 `error-codes.yml`，把所有系统、类别、错误码和严重程度展开为不可变注册表。

## 引入依赖

```xml
<dependency>
    <groupId>com.ruoyi</groupId>
    <artifactId>ruoyi-errorcode-loader</artifactId>
    <version>3.9.2</version>
</dependency>
```

## 普通 Java 使用

```java
ErrorCodeRegistry registry = new ErrorCodeLoader()
        .load(Path.of("config/error-codes.yml"));

ErrorCodeDefinition definition = registry
        .requireBySystemCodeAndErrorCode("ORDER", "ORDER-2001");

List<ErrorCodeDefinition> results = registry.query(ErrorCodeQuery.create()
        .systemName("订单系统")
        .categoryName("订单创建")
        .errorCode("ORDER-2001"));
```

## Spring Boot 注册为 Bean

```java
@Configuration
public class ErrorCodeConfiguration
{
    @Bean
    public ErrorCodeRegistry errorCodeRegistry(
            @Value("${error-code.config-location}") Resource resource) throws IOException
    {
        try (InputStream inputStream = resource.getInputStream())
        {
            return new ErrorCodeLoader().load(inputStream);
        }
    }
}
```

```yaml
error-code:
  config-location: file:./config/error-codes.yml
```

加载器只在创建 Bean 时读取一次文件，不会定时检查。替换 YAML 后重启应用即可。

## 查询能力

- 获取全部错误码：`getAll()`
- 按系统编码和错误码唯一查询：`findBySystemCodeAndErrorCode(...)`
- 单独按错误码查询：`findByErrorCode(...)`，可能跨系统返回多条
- 按系统名称、类别名称和错误码查询：`findByNames(...)`
- 使用 `ErrorCodeQuery` 按系统、类别、错误码、严重程度和关键字组合查询

加载时会校验严重程度引用和同一系统内的重复错误码。配置不合法时抛出 `ErrorCodeLoadException`，使应用启动失败，避免带着错误配置运行。
