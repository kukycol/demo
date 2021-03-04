##### web springboot actuator项目监控

###### 官方文档：https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/html/production-ready-features.html#production-ready-endpoints-enabling-endpoints

###### 博客文件：https://www.cnblogs.com/zwqh/p/11851300.html

1、依赖

```
 		<dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
```

2、配置文件：http默认开启info和health两个端点，jmx默认开启全部端点

```
management:
  endpoints:
    #false是禁用所有端点
    enabled-by-default: false
#  endpoint:
#    #true启用单个端点
#    info:
#      enabled: true
#    health:
#      enabled: true
    web:
      exposure:
        #开启web默认的所有端点
        include: "*"
    jmx:
      exposure:
        include: "*"
  #actuator独立端口
  server:
    port: 8081
```

3、jmx工具：在java安装目录下的bin文件下的jmc.exe

```
#关闭springboot应用(仅支持post请求)
http://localhost/actuator/shutdown
#指标信息
http://localhost/actuator/metrics
#健康信息
http://localhost/actuator/health
#线程集合
http://localhost:8081/actuator/threaddump
#系统信息
http://localhost:8081/actuator/env
```

```
#heapdump，在浏览器输入地址，会自动下载一个heapdump，使用visualvm工具
http://localhost:8081/actuator/heapdump
```

4、原理
博客：https://blog.csdn.net/shupili141005/article/details/61476546

```
endpoint暴露方式由http、jmx
将端点(Endpoint)适配委托给MVC层策略端点(MvcEndpoint)，
再通过端点MVC适配器(EndpointMvcAdapter)将端点暴露为HTTP请求方式的MVC端点，
最后分别使用端点自动配置(EndpointAutoConfiguration)和MVC方式暴露端点的配置(EndpointWebMvcManagementContextConfiguration)来注入端点组件和端点处理程序映射组件、MVC端点注册表组件、MVC端点组件。
其中，端点处理程序映射(EndpointHandlerMapping)通过Spring MVC方式来暴露MVC端点
```

