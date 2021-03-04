##### mybatis 半自动持久层

1、依赖

```
   <properties>
        <mybatis.version>2.0.0</mybatis.version>
        <mapper.version>4.0.3</mapper.version>
        <pagehelper.version>1.2.12</pagehelper.version>
    </properties>
    
    <dependencies>

        <!--mybatis-->
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis.version}</version>
        </dependency>

        <!--tk-->
        <dependency>
            <groupId>tk.mybatis</groupId>
            <artifactId>mapper</artifactId>
            <version>${mapper.version}</version>
        </dependency>

        <!--pageHelper mybatis分页插件-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>${pagehelper.version}</version>
        </dependency>

  
    </dependencies>
```

2、配置文件

```
# mybatis
mybatis:
  # 映射体
  type-aliases-package: com.ezm.entity
  # 驼峰转换
  configuration.map-underscore-to-camel-case: true
  # 控制台打印
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl

# pagehelper mybatis插件
pagehelper:
  helperDialect: mysql
  # 分页合理化参数，默认值为false。当该参数设置为 true 时，pageNum<=0 时会查询第一页， pageNum>pages（超过总数时），会查询最后一页
  reasonable: true
  # 支持通过 Mapper 接口参数来传递分页参数
  supportMethodsArguments: true
```

3、MapperScan注解

```
在springboot启动类上添加@MapperScan
```

4、mapper常用注解

```
#查询注解
@Select("")
#动态查询
@SelectProvider(type=ClassName.class,method="methodName")
#添加注解
@Insert("")
#动态添加
@InsertProvider(type=ClassName.class,method="methodName")
#删除注解
@Delete("")
#动态删除
@DeleteProvider(type=ClassName.class,method="methodName")
#更新注解
@Update("")
#动态更新
@UpdateProvider(type=ClassName.class,method="methodName")
```

5、例子

```
 #动态查询
 @SelectProvider(method = "findUserSearch",type = MybatisMapperProvider.class)
 List<User> findUserSearch(User user);

 #内部类
 class MybatisMapperProvider{
 
	   //动态查询
        public String findUserSearch(User user) {
            StringBuffer sb = new StringBuffer();
            sb.append("select * from user where 1 = 1");
            if (StringUtils.isNotBlank(user.getEmail())){
                sb.append(" and email = #{email}");
            }
            if (StringUtils.isNotBlank(user.getMobile())){
                sb.append(" and mobile = #{mobile}");
            }
            if (StringUtils.isNotBlank(user.getUsername())){
                sb.append(" and username = #{username}");
            }
            return sb.toString();
        }
        
 }
```

6、pagehelper

```
1、pagehelper开启分页后，先查询条数,再进行数据查询
2、
```

