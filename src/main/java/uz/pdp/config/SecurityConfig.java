package uz.pdp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder().encode("111"))
                .roles("SUPER_ADMIN")
                .authorities("ADD_PRODUCT", "GET_ONE_PRODUCT", "GET_ALL_PRODUCT", "UPDATE_PRODUCT", "DELETE_PRODUCT")
                .and()
                .withUser("moderator").password(passwordEncoder().encode("222"))
                .roles("MODERATOR")
                .authorities("ADD_PRODUCT", "UPDATE_PRODUCT")
                .and()
                .withUser("operator").password(passwordEncoder().encode("333"))
                .roles("OPERATOR")
                .authorities("ADD_PRODUCT","GET_ONE_PRODUCT", "GET_ALL_PRODUCT");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
