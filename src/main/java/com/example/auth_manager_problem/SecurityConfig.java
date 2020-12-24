package com.example.auth_manager_problem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@EnableWebSecurity(debug = true)
public class SecurityConfig {

    @Bean("authManager1")
//    @Primary
    static AuthenticationManager authenticationManager1() {
        return new ProviderManager(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return new UsernamePasswordAuthenticationToken("user", "credentials");
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        });
    }

    @Bean("authManager2")
    static AuthenticationManager authenticationManager2() {
        return new ProviderManager(new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return new UsernamePasswordAuthenticationToken("subuser", "credentials");
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        });
    }

    @Configuration
    @Order(2)
    public static class SecurityConfig1 extends WebSecurityConfigurerAdapter {
        public SecurityConfig1() {
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/hello");
        }

        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return authenticationManager1();
        }
    }

    @Configuration
    @Order(1)
    public static class SecurityConfig2 extends WebSecurityConfigurerAdapter {
        public SecurityConfig2() {
        }

        @Override
        protected AuthenticationManager authenticationManager() throws Exception {
            return authenticationManager2();
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring()
                    .antMatchers("/hello");
        }
    }
}
