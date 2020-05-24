package ru.mgilivanov.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import ru.mgilivanov.project.controller.*;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        BasicAuthenticationEntryPoint entryPoint = new BasicAuthenticationEntryPoint();
        entryPoint.setRealmName("project");
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.to("health", "info")).permitAll()
                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ACTUATOR")
                .antMatchers(CreditController.CREDIT_ISSUE + "/**").hasAnyRole("CRM")
                .antMatchers(CreditController.CREDIT_EOD_ERRORS + "/**").hasAnyRole("ABS", "CRM", "EOD")
                .antMatchers(CreditController.CREDIT_INFO + "/**").hasAnyRole("ABS", "CRM")
                .antMatchers(CreditController.CREDITS_BY_DAY + "/**").hasAnyRole("ABS", "CRM")
                .antMatchers(CreditController.CREDITS_FOR_CLIENT + "/**").hasAnyRole("CRM")
                .antMatchers(CreditController.CREDIT_PREPAYMENT + "/**").hasAnyRole("CRM")
                .antMatchers(ClientController.CLIENT_ADD + "/**").hasAnyRole("CRM")
                .antMatchers(ClientController.CLIENT_EDIT + "/**").hasAnyRole("CRM")
                .antMatchers(ClientController.CLIENT_INFO + "/**").hasAnyRole("CRM")
                .antMatchers(PayDocumentController.PAYDOC_CREATE + "/**").hasAnyRole("PROCESSING")
                .antMatchers(PayDocumentController.PAYDOC_REESTR + "/**").hasAnyRole("ABS", "CRM")
                .antMatchers(PrepaymentController.CREDIT_PREPAYMENT + "/**").hasAnyRole( "CRM")
                .antMatchers(CloseDayController.CLOSE_DAY_RUN + "/**").hasAnyRole("EOD")
                .antMatchers(CloseDayController.EOD_STATUS + "/**").hasAnyRole("EOD", "ABS", "CRM", "PROCESSING")
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .httpBasic().authenticationEntryPoint(entryPoint)
                .and()
                .csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .mvcMatchers(
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html"
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder() {
        };
    }
}
