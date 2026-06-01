# 租客版本的 Java 微信小程序后台

## 模块

- `bdhb-base`: 基础消息、异常、JOOQ 和通用 CRUD 能力。
- `weixin-pay`: 微信支付工具包。
- `bdhb-weixin`: Spring Boot 微信小程序后台服务。

## 构建

根目录已经提供 Maven 聚合配置，可以一次性编译所有模块：

```bash
mvn test
```

普通构建不会连接数据库重新生成 JOOQ 代码。需要重新生成 JOOQ 表类时，在数据库配置可用的情况下执行：

```bash
mvn -pl bdhb-weixin -Pgenerate-jooq generate-sources
```
