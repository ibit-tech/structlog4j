# structlog4j

结构化日志对于日志的收集的作用挺大的，借鉴了[jacek99/structlog4j](https://github.com/jacek99/structlog4j)，根据自身的业务场景，基于`SLF4J`实现了`structlog4j`。

## 相关引用

### Gradle

```
// 基础包
compile 'tech.ibit:structlog4j-api:1.0'

// 支持json, yaml格式等扩展
compile 'tech.ibit:structlog4j-extend:1.0'

```


### Maven

```xml
    <!--基础包-->
    <dependency>
        <groupId>tech.ibit</groupId>
        <artifactId>structlog4j-api</artifactId>
        <version>1.0</version>
    </dependency>
    
    <!--扩展包-->
    <dependency>
        <groupId>tech.ibit</groupId>
        <artifactId>structlog4j-extend</artifactId>
        <version>1.0</version>
    </dependency>
```


## 概述

structlog4j的核心思想就是将日志已key-value的方式呈现，方便日期切分。

### 日志保留字段

```
_message: 消息内容
_errorMessage: 异常信息
_stackTrace: 异常stack trace信息

```

### 字符串处理

业务需要，将日志单行输入，所以对以下字符进行了处理, ":"前原字符串，":" 后的是处理后的字符

```
\t: \\t
\r: \\r
\n: \\n
```

### 默认日志格式(key\-value) 

```
_message=Something error!&user=ibit-tech_errorMessage=Test Exception
```

**说明**

 *  默认方式引入`structlog4j-api`即可
 * `key-value`的方式通过`&`进行分割，将`&`转为`'%26'`

### json日志格式

```json
{"_message":"Something error!","user":"ibit-tech","_errorMessage":"Test Exception"}
```

**修改全局formatter方法**：

```
方法1（java代码）：
StructLog4J.setFormatter(JsonFormatter.getInstance());

方法2（classpath:/structlog4j.properties)：
formatter=tech.ibit.structlog4j.extend.JsonFormatter#getInstance
```

**说明**

 * 需要引入`structlog4j-extend`
 * "#"后面为获取实例方法

### yaml日志格式

```
hostname: localhost\nip: 127.0.0.1
```

**修改全局formatter方法**：

```
方法1（java代码）：
StructLog4J.setFormatter(YamlFormatter.getInstance());

方法2（classpath:/structlog4j.properties)：
formatter=tech.ibit.structlog4j.extend.YamlFormatter#getInstance
```

**说明**

 * 需要引入`structlog4j-extend`
 * "\\n"：表示换行符号，读取到日志之后，需要将"\\n"转为"\n"才能正确显示
 * "#"后面为获取实例方法

### 自定义日志格式

实现`tech.ibit.structlog4j.Formatter#format`方法即可

```
tech.ibit.structlog4j.Formatter {
    /**
     * 格式化（待实现）
     *
     * @param kvMap 兼职map
     * @return 格式化后的文本
     */
    String format(Map<String, ?> kvMap);
}
``` 

### 异常信息处理

 * 默认方式，`StructLog4J.isTransStackTrace()` == `true`, 日志中会出现`_errorMessage`和`_stackTrace`字段，`_stackTrace`进行了转义，单行显示
 * 当`StructLog4J.isTransStackTrace()` == `false`, 日志中只出现`_errorMessage`，然后之后就将异常信息在接下来的日志打印出来（多行）  
 

**eg**:

```
# 单行打印stackTrace
_message=Something error&user=ibit-tech&_errorMessage=Test Exception&_stackTrace=java.lang.RuntimeException: Test Exception\n\tat tech.ibit.demo.structlog4j.Demo.main(Demo.java:32)\n

# 多行答应stackTrace
_message=Something error&user=ibit-tech&_errorMessage=Test Exception
java.lang.RuntimeException: Test Exception
	at tech.ibit.demo.structlog4j.Demo.main(Demo.java:33)
``` 

**修改全局transStackTrace方法**：

```
方法1（java代码）：
StructLog4J.setTransStackTrace(true|false);

方法2（classpath:/structlog4j.properties)：
transStackTrace=true|false
```

### structlog4j.properties说明：

```
# 指定formatter 创建方式（工厂方法）
formatter=tech.ibit.structlog4j.extend.JsonFormatter#getInstance

# 指定异常的stackTrace日志是否需要转化
transStackTrace=true
```

## 用法

### 使用key-value的方式

```
log.error("Something error", "user", "ibit-tech", "age", 100);
```

### 实现`ToLog`对key-value进行包装

```
log.error("Something error", (ToLog) () -> new Object[] {"user", "ibit-tech", "age", 101});
```

### 异常处理

```
log.error("Something error", "user", "ibit-tech", "age", 100, new RuntimeException("Test Exception"));
```

### 混合使用

```
log.error("Something error", (ToLog) () -> new Object[] {"user", "ibit-tech"}, "age", 101);

log.error("Something error", (ToLog) () -> new Object[] {"user", "ibit-tech"}, "age", 101, (ToLog) () -> new Object[] {"city", "sz"}, new RuntimeException("Test Exception"));
```

### POJO实现`MapToLog`，toLog()会返回POJO中所有字段

```
@Test
public void toLog() {
    User user = new User("ibit-tech", 21);
    Assert.assertEquals("[name, ibit-tech, age, 21]", Arrays.asList(user.toLog()).toString());
}
	
@Value
public class User implements MapToLog {
    private String name;
    private int age;
}
```

**说明**

 * 需要引入`structlog4j-extend`

## License

Apache License 2.0 
