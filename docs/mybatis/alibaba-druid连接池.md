##### alibaba-druid连接池

1、依赖

```
    <properties>
        <druid.version>1.1.17</druid.version>
    </properties>
    
    <dependencies>

        <!--druid-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>${druid.version}</version>
        </dependency>

        <!--mysql驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        
    </dependencies>        
```

2、配置文件

```
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1/admin?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowMultiQueries=true
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: Touzhen.98
    #druid 监控
    druid:
      #初始设置连接数数量,连接数=((CPU核心数*2)+有效磁盘数)
      initialSize: 5
      #最小连接池数量
      minIdle: 2
      #最大连接池数量
      maxActive: 10
      #获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，
      #如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
      #      maxWait: 60000
      # 连接池中的minIdle数量以内的连接，空闲时间超过minEvictableIdleTimeMillis，则会执行keepAlive操作。
      keepAlive: true
      #配置间隔多久才进行一次检测，检测需要关闭的空闲连接
      timeBetweenEvictionRunsMillis: 60000
      #连接在池中最小生存的时间
      minEvictableIdleTimeMillis: 30000
      #用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
      #如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
      validationQuery: select 'x'
      #      validationQuery: SELECT 1 FROM DUAL
      #	单位：秒，检测连接是否有效的超时时间。
      #底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法
      validationQueryTimeout: 3
      #	建议配置为true，不影响性能，并且保证安全性。
      # 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效
      testWhileIdle: true
      #	申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
      testOnBorrow: false
      #	归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
      testOnReturn: false
      #是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，
      #比如说oracle。在mysql下建议关闭。
      poolPreparedStatements: false
      # 配置监控统计拦截的filters，去掉后监控界面sql无法统计，
      #'wall'用于防火墙，SpringBoot中没有log4j，我改成了log4j2
      filters: stat,wall
      #要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
      #在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
      maxPoolPreparedStatementPerConnectionSize: -1
      useGlobalDataSourceStat: true
      # 通过connectProperties属性来打开mergeSql功能；慢SQL记录
      connectionProperties: druid.stat.mergeSql=true;druid.stat.slowSqlMillis=500
      # 配置StatFilter
      # 配置StatFilter
      web-stat-filter:
        #默认为false，设置为true启动
        enabled: true
        url-pattern: "/*"
        exclusions: "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*"
      #spring监控
      aop-patterns: com.ezm.*
      #配置StatViewServlet
      stat-view-servlet:
        url-pattern: "/druid/*"
        #允许那些ip
        allow:
        login-username: admin
        login-password: admin
        #禁止那些ip
        #deny: 192.168.1.102
        #是否可以重置
        reset-enable: true
        #启用
        enabled: true
```

3、访问地址

```
http://localhost:port/druid
```

