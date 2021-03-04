Springboot知识点：

- 默认端口：8080
- 默认最高并发量：200

Springboot面试题：

- 什么是Springboot?
  - SpringBoot是建立在spring框架之上。使用spring boot启动，我们可以避免了之前spring必须要写的配置和代码，所以springboot可以帮助我们减少工作量，更好的使用现有spring功能。
- 如何重新加载springboot上的更改，而无需重新启动服务器?
  - 开发环境的话可以是使用springboot-devtools热部署，
    - 如果时java代码的话，当修改了java代码后idea开发工具会自动保存，热部署就会自动编译
    - 如果是页面的话，需要修改一下页面缓存配置为false，这样你修改完html页面时侯，在浏览器刷新一下就会生效
  - 热部署原理：
    - 在编辑器上启动项目，修改代码后，编辑器自动触发编译替换掉历史的class文件后，项目检测到有文件变更后重启springboot项目
    - springboot对重新启动技术提供两种类加载器，base类加载器，restart加载器，不改变的类被加载到base加载器，修改的类被加载到restart加载器
- springboot 中的监视器是什么?
  - springboot actuator，springboot actuator它是spring启动框架中的功能之一，actuator监视器可以查看生成环境中正在运行的应用程序状态。
  - 原理
    - 
- 如何在springboot 中禁用actuator端点安全？
  - 默认情况下，所有敏感的http端点都是安全的。
  - 可以搭配安全框架security一起使用，只有具有actuator角色才能访问。
- 如何在自定义端口上运行springboot 应用程序？
  - 在springboot配置文件中（application.yml）配置server.port: 8090
- 什么时YAML?
  - YAML是一种人类可读的数据序列化语言。通常用于配置文件
  - 与属性文件相比，如果我们想要在配置文件中添加复杂的属性，YAML文件就更加结构化，而且更少混肴，可以看出YAML具有分层配置数据。
- 如何实现springboot应用程序的安全性？
  - 可以使用spring的安全框架，security或者是shiro框架
- 什么是swagger？你用springboot实现了它吗？
  - 