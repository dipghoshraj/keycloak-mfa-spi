package com.mfa.magiclink.linkutils;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.email.EmailException;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import org.keycloak.models.UserModel;
import org.keycloak.services.resources.LoginActionsService;
import org.keycloak.services.resources.RealmsResource;
import org.keycloak.services.Urls;
import org.keycloak.common.util.Time;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

public class MagiclinkUtils {
    public static String generateMagicLink(AuthenticationFlowContext context, UserModel user) {

        int tokenExpiration = 600; // 10 minutes
        KeycloakSession session = context.getSession();

        int absoluteExpirationInSecs = Time.currentTime() + tokenExpiration;
        MagicLinkActionToken token = new MagicLinkActionToken(user.getId(), absoluteExpirationInSecs, user.getEmail(), session.getContext().getClient().getClientId());

        UriInfo uriInfo = session.getContext().getUri();
        RealmModel realm = session.getContext().getRealm();

        UriBuilder builder = actionTokenBuilder( uriInfo.getBaseUri(), token.serialize(session, realm, uriInfo), token.getIssuedFor());

        // and then set it back
        session.getContext().setRealm(realm);
        return builder.build(realm.getName()).toString();
    }

    private static UriBuilder actionTokenBuilder(URI baseUri, String tokenString, String clientId) {
        return Urls.realmBase(baseUri)
            .path(RealmsResource.class, "getLoginActionsService")
            .path(LoginActionsService.class, "executeActionToken")
            .queryParam("key", tokenString)
            .queryParam("client_id", clientId);
    }


    public static boolean sendMagicLinkEmail(KeycloakSession session, UserModel user, String link){
        RealmModel realm = session.getContext().getRealm();
        try {
            EmailTemplateProvider emailTemplateProvider = session.getProvider(EmailTemplateProvider.class);
        String realmName = realm.getName();
        List<Object> subjAttr = ImmutableList.of(realmName);
        Map<String, Object> bodyAttr = Maps.newHashMap();
        bodyAttr.put("realmName", realmName);
        bodyAttr.put("magicLink", link);
        emailTemplateProvider
          .setRealm(realm)
          .setUser(user)
          .setAttribute("realmName", realmName)
          .send("otpSubject", subjAttr, "magic-link-email.ftl", bodyAttr);
        return true;
        } catch (EmailException e) {
            System.err.println(e);
            e.printStackTrace();
            return false;
        }
    }
}
