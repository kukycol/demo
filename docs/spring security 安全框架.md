##### spring security 安全框架 

###### 1、依赖

```
       <!--security-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>

        <!--spring security5-->
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
        </dependency>
```

###### 2、配置类

```
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private SecurityImpl securityImpl;

    @Resource
    private SecuritySource securitySource;

    @Resource
    private AccessManager accessManager;

    @Resource
    private RuleHandler ruleHandler;

    @Resource
    private LoginFailureHandler loginFailureHandler;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;


    /**
     * 身份验证
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityImpl).passwordEncoder(passwordEncoder());
    }

    /**
     * 加密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户验证
     */
    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }


    /**
     * 主配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                /**授权请求*/
                .authorizeRequests()
                /**动态授权*/
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(securitySource);
                        object.setAccessDecisionManager(accessManager);
                        return object;
                    }
                })
                .anyRequest().authenticated()
                .and()
                .formLogin()
                /**自定义登录页*/
                .loginPage("/Login")
                /**自定义登录请求*/
                .loginProcessingUrl("/login.lo")
                /**账号密码属性名*/
                .usernameParameter("account")
                .passwordParameter("password")
                /**登录成功后转发到页面*/
//                .successForwardUrl("/user/index")
                /**登录失败拦截器*/
                .failureHandler(loginFailureHandler)
                /**登录成功拦截器*/
                .successHandler(loginSuccessHandler)
                .and()
                /**权限不足拦截器*/
                .exceptionHandling().accessDeniedHandler(ruleHandler)
                .and()
                .csrf().disable()
                /**登出请求*/
                .logout()
                .logoutUrl("/logout")
                .and()
                /**记住密码*/
                .rememberMe()
                .rememberMeParameter("reb")
                /**记住密码多长时间*/
                .tokenValiditySeconds(60 * 60 * 2)
                .and()
                //解决控制台报错Refused to display 'http://127.0.0.1:8070/default_sso_heartbeat.html' in a frame because it set 'X-Frame-Options' to 'DENY'.
                .headers().frameOptions().disable();
        ;
    }


    /**
     * 放行
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().mvcMatchers(
                "login.lo", //登录请求
                "/Login", //登录页
                "/favicon.ico",
                "/druid/**", //swagger3数据请求
                "/v3/**", //swagger3数据请求
                "/swagger**/**", //swagger3数据请求
                "/webjars/**", //swagger3静态资源
                "/doc.html", //swagger3主页面
                "/404.html", //400页面
                "/static/**" //thymeleaf动态页面静态资源

        );
    }
}
```

###### 3、自定义拦截器

1）登录成功

```
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.err.println(authentication.getPrincipal());
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.loginSuccessful)));
    }
    
}
```

2）登录失败

```
@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

        response.setContentType("application/json;charset=utf-8");

        //账号或密码错误
        if (e instanceof BadCredentialsException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.wrongPassword)));

        //账号锁定
        } else if (e instanceof LockedException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.accountNonLocked)));

        //密码过期
        } else if (e instanceof CredentialsExpiredException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.credentialsNonExpired)));

        //账号过期
        } else if (e instanceof AccountExpiredException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.accountNonExpired)));

        //账号未启用
        } else if (e instanceof DisabledException) {
            response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.enabled)));

        }

    }


}
```

3）权限不足

```
@Component
public class RuleHandler implements AccessDeniedHandler {
    
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        e.printStackTrace();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(SecurityResult.result(ResultEnum.permissionDenied)));
    }
    
}
```

###### 4、动态权限赋值

1）访问决策管理器

```
@Component
public class AccessManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        for (ConfigAttribute configAttribute : configAttributes) {
            //不存在,不需要绑定任何角色就可以访问
            String attribute = configAttribute.getAttribute();
            System.out.println(authentication);
            if ("ROLE_guest".equals(attribute)){
                if (authentication instanceof AnonymousAuthenticationToken){
                    System.out.println("匿名登录");
                    throw new AccessDeniedException("权限不足，无法访问");
                }else {
                    System.out.println("其他类型的用户");
                    return;
                }
            }

            //存在,需要对应的角色才可以访问
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(configAttribute.getAttribute())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足，无法访问！");
        
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}
```

2）筛选调用安全性元数源

```
@Component
public class SecuritySource implements FilterInvocationSecurityMetadataSource {



    @Resource
    private SecurityRuleMapper securityRuleMapper;
    @Resource
    private SecurityRoleMapper securityRoleMapper;



    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        //请求路径
        String requestUrl = ((FilterInvocation) object).getRequestUrl();

        //查表是否存在请求路径
        SecurityRule securityRule = securityRuleMapper.findByRule(requestUrl);

        //请求路径不存在表中，赋予游客角色
        if (CheckUtil.isBlank(securityRule)){
            return SecurityConfig.createList("ROLE_guest");
        }

        //查请求路径拥有的角色
        List<SecurityRole> byRuleId = securityRoleMapper.findByRuleId(securityRule.getId());

        //资源路径角色赋予框架
        String[] roleNameArray = new String[byRuleId.size()];
        for (int i = 0; i < byRuleId.size(); i++) {
            roleNameArray[i] = byRuleId.get(i).getRole();
        }

        return SecurityConfig.createList(roleNameArray);


    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


}
```

###### 5、登录验证

```
@Service
public class SecurityImpl implements UserDetailsService {

    //user mapper
    @Resource
    private SecurityUserMapper securityUserMapper;

    //role mapper
    @Resource
    private SecurityRoleMapper securityRoleMapper;


    //验证用户名是否存在
    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {

        //查库用户名是否存在
        SecurityUser securityUser = securityUserMapper.findByAccount(account);


        //账号不存在
        if (CheckUtil.isBlank(securityUser)){
            throw new UsernameNotFoundException(account);
        }

        //用户角色列表
        securityUser.setRoleList(securityRoleMapper.findByUserId(securityUser.getId()));


        return securityUser;
    }
}
```

###### 6、整合页面

1）用户

1.1）用户列表+搜索条件+分页条件 √

```
1、搜索条件不需要validation数据验证
2、分页条件是必要的需要做数据验证,page、limit参数不能为null，limit做限制1-100范围允许，page做限制大于1。
```

1.2）用户添加

```
1、所有字段需要做validation数据验证
2、唯一值字段需要查数据，做唯一判断。
3、添加成功后，添加默认user角色
```

1.3.1）用户编辑

```
1、所有字段需要做validation数据验证
2、根据id查询后，如果存在则跟前端带过来的字段做对比，值不相同的字段需要update
```

1.3.2）用户角色授权和取消授权

```
1、userId、roleId、flag三个字段不允许为null
2、flag = true，添加，已存在则提示已存在，不存在则添加，flag = false，相反
3、userId、roleId都需要查询验证是否存在
```

1.4）用户删除

```
1、删除用户角色后再删除用户
```

1.5）用户停用

1.6）用户启用

1.7）密码重置

2）角色

3）权限

###### 7、解释

```
#会话配置
1、会话过期时间（默认是30分，单位是s）
server.servlet.session.timeout=30m
2、
```

