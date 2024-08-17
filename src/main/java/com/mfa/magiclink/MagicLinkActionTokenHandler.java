package com.mfa.magiclink;


import java.net.URI;

import org.keycloak.OAuth2Constants;
import org.keycloak.authentication.actiontoken.AbstractActionTokenHandler;
import org.keycloak.authentication.actiontoken.ActionTokenContext;
import org.keycloak.events.Details;
import org.keycloak.events.Errors;
import org.keycloak.models.ClientModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.protocol.oidc.OIDCLoginProtocol;
import org.keycloak.protocol.oidc.utils.RedirectUtils;
import org.keycloak.services.managers.AuthenticationManager;
import org.keycloak.services.util.ResolveRelative;
import org.keycloak.sessions.AuthenticationSessionModel;
import org.keycloak.events.EventType;


import jakarta.ws.rs.core.Response;



public class MagicLinkActionTokenHandler extends AbstractActionTokenHandler<MagicLinkActionToken>{

    public MagicLinkActionTokenHandler() {
        super(
            MagicLinkActionToken.TOKEN_TYPE,
            MagicLinkActionToken.class,
            "invalidRequestMessage",
            EventType.EXECUTE_ACTION_TOKEN,
            Errors.INVALID_REQUEST);
    }

    @Override
    public Response handleToken( MagicLinkActionToken token, ActionTokenContext<MagicLinkActionToken> tokenContext) {  
        UserModel user = tokenContext.getAuthenticationSession().getAuthenticatedUser();


    Long itatime = token.getIat() * 1000;
    Long currentTimeMillis = System.currentTimeMillis();
    Long differenceMillis = Math.abs(currentTimeMillis - itatime);
    long twoMinutesMillis = 3 * 60 * 1000;

    System.out.println(differenceMillis <= twoMinutesMillis);

    final AuthenticationSessionModel authSession = tokenContext.getAuthenticationSession();
    user.setEmailVerified(true);

    String nextAction =
        AuthenticationManager.nextRequiredAction(
            tokenContext.getSession(),
            authSession,
            tokenContext.getRequest(),
            tokenContext.getEvent());
    return AuthenticationManager.redirectToRequiredActions(
        tokenContext.getSession(),
        tokenContext.getRealm(),
        authSession,
        tokenContext.getUriInfo(),
        nextAction);
  }

    }
