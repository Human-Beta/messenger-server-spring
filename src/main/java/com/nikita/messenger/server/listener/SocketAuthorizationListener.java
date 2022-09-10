package com.nikita.messenger.server.listener;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
public class SocketAuthorizationListener implements AuthorizationListener {

    private static final Logger LOG = LoggerFactory.getLogger(SocketAuthorizationListener.class);

    private static final String TOKEN_URL_PARAM = "token";

    @Autowired
    private ResourceServerTokenServices tokenServices;

    @Override
    public boolean isAuthorized(final HandshakeData handshakeData) {
        boolean result = false;
        final List<String> tokens = handshakeData.getUrlParams().get(TOKEN_URL_PARAM);

        if (isEmpty(tokens)) {
            LOG.error("There is no {} parameter in the URL params", TOKEN_URL_PARAM);
        }

        final String token = tokens.get(0);
        try {
            tokenServices.loadAuthentication(token);

            result = true;
            LOG.debug("Socket connection with token {} is authorized", token);
        } catch (final InvalidTokenException e) {
            LOG.debug("Socket connection with token {} is not authorized", token, e);
        }

        return result;
    }
}
