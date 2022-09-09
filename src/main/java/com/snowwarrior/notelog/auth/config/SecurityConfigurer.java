package com.snowwarrior.notelog.auth.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import static com.snowwarrior.notelog.auth.config.JwtDsl.securityConfigurationAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfigurer {
    private final CorsFilter corsFilter;

    private final SecurityProblemSupport securityProblemSupport;
    @Autowired
    public SecurityConfigurer(CorsFilter corsFilter, SecurityProblemSupport securityProblemSupport) {
        this.corsFilter = corsFilter;
        this.securityProblemSupport = securityProblemSupport;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                //将401和403的两种情况的处理托管给problem系统
                .authenticationEntryPoint(securityProblemSupport)
                .accessDeniedHandler(securityProblemSupport)
                .and()
                //关闭csrf
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                //控制不需要授权进入的接口
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/app/**/*.{js,html}").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/user/register").permitAll()
                .anyRequest().authenticated()
                .and()
                //不需要会话状态管理
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //加入jwt验证filter
                .apply(securityConfigurationAdapter());
        return httpSecurity.build();
    }
}
