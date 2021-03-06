##### swagger

官网：https://xiaoym.gitee.io/knife4j/

1、依赖

```
   <properties>
        <knife4j.version>3.0.2</knife4j.version>
        <springfox.version>3.0.0</springfox.version>
    </properties>
    
    <dependencies>
        <!--springboot-swagger3 -->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
            <version>${springfox.version}</version>
        </dependency>

        <!--xiaoymin-bootstrap-ui -->
        <dependency>
            <groupId>com.github.xiaoymin</groupId>
            <artifactId>knife4j-spring-boot-starter</artifactId>
            <version>${knife4j.version}</version>
        </dependency>

    </dependencies>
```

2.配置

```
knife4j:
  #开启knife4j
  enable: true
  #是否开启一个默认的跨域配置,该功能配合自定义Host使用
  cors: false
  #是否开启生产环境,关闭swagger文档
  production: false
  #swagger 不需要要权限可以访问,设置一个basic登录功能,默认admin/123321
  basic:
    enable: true
    username: test
    password: 12313
  #	自定义文档集合
  #  documents:
  #    -
  #      group: 2.X版本
  #      name: 接口签名
  #      locations: classpath:sign/*
  setting:
    #语言
    language: zh-CN
    #是否显示实体类文件列表
    enableSwaggerModels: true
    #自定义实体类列表名称
    swaggerModelName: 实体类列表
    #是否显示文档管理
    enableDocumentManage: true
    #是否开启界面中对某接口的版本控制,如果开启，后端变化后Ui界面会存在小蓝点
    enableVersion: true
    #是否在每个Debug调试栏后显示刷新变量按钮,默认不显示
    enableReloadCacheParameter: false
    #调试Tab是否显示AfterScript功能,默认开启
    enableAfterScript: false
    #具体接口的过滤类型
    enableFilterMultipartApiMethodType: POST
    #针对RequestMapping的接口请求类型,在不指定参数类型的情况下,如果不过滤,默认会显示7个类型的接口地址参数,如果开启此配置,默认展示一个Post类型的接口地址
    enableFilterMultipartApis: false
    #是否开启请求参数缓存
    enableRequestCache: true
    #是否启用Host
    enableHost: false
#    enableHostText: 192.168.0.193:8000
    #是否开启自定义主页内容
    enableHomeCustom: false
    #主页内容Markdown文件路径
    homeCustomLocation: classpath:你值得拥有.md
    #是否禁用Ui界面中的搜索框
    enableSearch: false
    #是否显示Footer
    enableFooter: false
    #是否开启自定义Footer
    enableFooterCustom: true
    #接口文档页面底部提示
    footerCustomContent: Apache License 2.0 | Copyright  2020-[XAdmin后台系统](http://localhost:${server.port})
    #是否开启动态参数调试功能
    enableDynamicParameter: true
    #启用调试
    enableDebug: true
    #显示OpenAPI规范
    enableOpenApi: true
    #显示服务分组
    enableGroup: true


swagger:
  enable: true
  application-name: ${spring.application.name}
  application-version: 1.0
  application-description: ${spring.application.name} Swagger接口文档
  #调试地址
  try-host: 127.0.0.1:${server.port}
  termsOfServiceUrl: 服务Url
  groupName: ${spring.application.name}
```

3、常用注解

```

```

