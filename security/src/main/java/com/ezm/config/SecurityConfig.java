package com.ezm.config;


import com.ezm.handler.*;
import com.ezm.rule.AccessManager;
import com.ezm.rule.SecuritySource;
import com.ezm.service.impl.SecurityImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;

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
    private MyInvalidSessionStrategy myInvalidSessionStrategy;

    @Resource
    private MyExpiredSessionStrategy myExpiredSessionStrategy;

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
//                .rememberMe()
//                .rememberMeParameter("reb")
                /**记住密码多长时间*/
//                .tokenValiditySeconds(60 * 60 * 2)
//                .and()
                .sessionManagement()
                /**会话失效后的处理*/
                .invalidSessionStrategy(myInvalidSessionStrategy)
                /**设置单用户的 Session 最大并发会话数量，-1 表示不受限制*/
//                .maximumSessions(1)
                /**其他地方登录session失效处理策略*/
//                .expiredSessionStrategy(myExpiredSessionStrategy)
//                .expiredUrl("/logout")
                /**设置为 true，表示某用户达到最大会话并发数后，新会话请求会被拒绝登录*/
//                .maxSessionsPreventsLogin(true)
//                .and()
                /**防御会话固定攻击*/
                .sessionFixation()
                /**登录后重新生成session*/
                .newSession()
                .and()
                //解决控制台报错Refused to display 'http://127.0.0.1:8070/default_sso_heartbeat.html' in a frame because it set 'X-Frame-Options' to 'DENY'.
                .headers().frameOptions().disable();
        ;
    }

    /**maxSessionsPreventsLogin(true)搭配使用*/
//    @Bean
//    public HttpSessionEventPublisher httpSessionEventPublisher(){
//        return new HttpSessionEventPublisher();
//    }

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
//                "/doc.html", //swagger3主页面
                "/404.html", //400页面
                "/static/**", //thymeleaf动态页面静态资源
                "/error" //thymeleaf动态页面静态资源

        );
    }
}
