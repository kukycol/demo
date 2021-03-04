##### web springboot-devtools热部署

1、依赖

```
 <dependencies>
        <!--devTools-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
            <scope>true</scope>
        </dependency>
 </dependencies>
 
 
<build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <!--如果没有该配置，devtools不会起作用，即应用不会restart-->
                    <fork>true</fork>
                </configuration>
            </plugin>
        </plugins>
 </build>

```

2、idea配置：

```
settings--->Build,Exwcution,Deployment--->Compiler--->✔Build project automatically
```

```
快捷键：ctrl+shift+alt+/ 找到registy... --->✔compiler.automake.allow.when.app.running
```

```
重启项目
```

3、消除页面缓存配置

```
spring:
  thymeleaf:
    cache: false
```

4、原理

```
springboot项目启动后(提供两种类加载器base and restart)--->修改代码后--->编译器自动触发编译替换掉旧的class文件(为修改的代码由base加载器加载、修改的代码由restart加载器加载)--->项目检测到文件更改后会重启项目
```

