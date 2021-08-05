package com.projectdevm8.stockalarm.ConfigEx.config;

import com.projectdevm8.stockalarm.service.implementation.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.concurrent.TimeUnit;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserService userService;

  public SecurityConfig(UserService userService) {
    this.userService = userService;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService);
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return NoOpPasswordEncoder.getInstance();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http .cors().and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/").permitAll()
        .antMatchers("/sign-up").permitAll()
        .antMatchers("/login").permitAll()
        .antMatchers("/*").hasRole("USER")
        .and()
            .rememberMe()
              .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
              .key("ceva bine securizat")
        .and()
        .logout()
            .logoutUrl("/logout")
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // daca ai csrf activat atunci folosesti post pentru logout.
            .clearAuthentication(true)
            .invalidateHttpSession(true)
            .deleteCookies("remember-me")
            .logoutSuccessUrl("/login");


  }
}