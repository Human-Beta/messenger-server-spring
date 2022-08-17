package com.nikita.messenger.server.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userService;

    @Value("${jwt.clientId}")
    private String clientId;
    @Value("${jwt.client-secret}")
    private String clientSecret;
    @Value("${jwt.accessTokenValiditySeconds}")
    private int accessTokenValiditySeconds;
    @Value("${jwt.refreshTokenValiditySeconds}")
    private int refreshTokenValiditySeconds;
    @Value("${jwt.authorizedGrantTypes}")
    private String[] authorizedGrantTypes;

    @Value("${jwt.scopes}")
    private String[] scopes;

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception {
//       TODO: clients in DB clients.jdbc()?
        clients.inMemory()
                .withClient(clientId)
                .secret(clientSecret)
                .accessTokenValiditySeconds(accessTokenValiditySeconds)
                .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                .authorizedGrantTypes(authorizedGrantTypes)
                .scopes(scopes);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .accessTokenConverter(accessTokenConverter())
                .userDetailsService(userService)
                .authenticationManager(authenticationManager);
    }

    @Bean
    JwtAccessTokenConverter accessTokenConverter() {
        return new JwtAccessTokenConverter();
    }
}