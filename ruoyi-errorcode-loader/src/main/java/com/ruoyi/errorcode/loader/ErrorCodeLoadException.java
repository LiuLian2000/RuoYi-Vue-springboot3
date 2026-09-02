package com.ruoyi.errorcode.loader;

/**
 * YAML 文件无法读取或内容不符合错误码配置约束。
 */
public class ErrorCodeLoadException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    public ErrorCodeLoadException(String message)
    {
        super(message);
    }

    public ErrorCodeLoadException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
