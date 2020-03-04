package com.VU.PSKProject.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    public LoginSecurityConfig(){
        super();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //TODO: finish configurating auth
        auth.inMemoryAuthentication()
                .withUser("worker1").password(passwordEncoder().encode("u1psswd")).roles("WORKER")
                .and()
                .withUser("lead1").password(passwordEncoder().encode("u2psswd")).roles("WORKER", "LEAD");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO: finish configurating http
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/lead/**").hasRole("LEAD")
                .antMatchers("/anonymous*/").anonymous()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html") // let's say it's login.html
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html") // let's say it's homepage.html
                .failureHandler(authenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(logoutSuccessHandler());
        super.configure(http);
    }

    private LogoutSuccessHandler logoutSuccessHandler() {
        //TODO: implement?
        return null;
    }

    private AuthenticationFailureHandler authenticationFailureHandler() {
        //TODO: implement?
        return null;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
