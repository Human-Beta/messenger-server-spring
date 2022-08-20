package com.nikita.messenger.server.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
public class ServerSecurityConfig {

    @Value("${allowed.origin}")
    private String allowedOrigin;
    @Bean
    public FilterRegistrationBean<CorsFilter>  customCorsFilter() {
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(allowedOrigin);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        final FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return bean;
    }

//    TODO: do i need it?
//    @Bean
//    public SecurityFilterChain defaultSecurityFilterChain(final HttpSecurity http) throws Exception {
//        http
//                .sessionManagement().sessionCreationPolicy(STATELESS)
//                .and()
//                .csrf().disable()
//                .cors().disable()
//                .authorizeRequests()
//                .anyRequest()
//                .permitAll();
////                .authenticated();
//
//        return http.build();
//    }
}
