package br.com.springbootessentials.config;

import static br.com.springbootessentials.config.SecurityConstants.SIGN_UP_URL;

import br.com.springbootessentials.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private CustomUserDetailsService customUserDetailsService;

  //  @Override
  //  protected void configure(HttpSecurity http) throws Exception {
  //    http.authorizeRequests()
  //        .antMatchers("/*/protected/**").hasAnyRole("USER")
  //        .antMatchers("/*/admin/**").hasAnyRole("ADMIN")
  //        .and()
  //        .httpBasic()
  //        .and()
  //        .csrf().disable();
  //  }
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
        //permitDefaultValues libera os endpoitns pra serem acessados atraves do navegador usando javascript. Caso eu na ofaca isso, o esquema de seguranca vai ficar travando requisicoes
        //de navegadores
        .and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
        .antMatchers("/*/protected/**").hasRole("USER")
        .antMatchers("/*/admin/**").hasRole("ADMIN")
        .and()
        .addFilter(new JWTAuthenticationFilter(authenticationManager()))
        .addFilter(new JWTAuthorizationFilter(authenticationManager(), customUserDetailsService));
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  //  @Autowired
  //  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
  //    auth.inMemoryAuthentication()
  //        .withUser("user").password("{noop}123456").roles("USER")
  //        .and()
  //        .withUser("admin").password("{noop}123456").roles("USER", "ADMIN");
  //  }

}
