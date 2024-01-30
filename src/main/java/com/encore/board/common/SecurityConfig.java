package com.encore.board.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity  //해당 어노테이션은 spring security 설정을 customizing 하기위함
//  WebSecurityConfigurerAdapter를 상속하는 방식은 deprecated(지원종료)되었다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // pre: 사전, post : 사후, 사전/사후에 인증/권한 검사 어노테이션 사용가능
public class SecurityConfig {

    @Bean
    public SecurityFilterChain myFilter(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf().disable() //csrf 보안공격에 대한 설정은 하지 않겠다는 의미
                .authorizeRequests() //특정 url에 대해서는 인증처리 하지 않고, 특정 url에 대해서는 인증처리 하겠다라는 설정
                    .antMatchers("/", "/author/create", "/author/login-page") //인증 미적용 url 패턴
                        .permitAll()//허용한다라는 의미ㅓㅓㅓㅓㅓㅕㅓㅕㅓㅛ
                .anyRequest().authenticated() //그외 요청은 모두 인증 필요
                .and()//만약에 세션을 사용하지 않으면 아래 내용 설정
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .formLogin()
                    .loginPage("/author/login-page")
//                스프링 내장메서드를 사용하기 위해 /doLogin url사용
                    .loginProcessingUrl("/doLogin")
                        .usernameParameter("email")
                        .passwordParameter("pw")
                    .successHandler(new LoginSuccessHandler())
                .and()
                .logout()
                    .logoutUrl("/doLogout") //spring security의 doLogout기능 그대로 사용
                .and()
                .build();
    }

}
