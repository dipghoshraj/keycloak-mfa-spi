package com.mfa.magiclink;

import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import com.mfa.magiclink.linkutils.MagiclinkUtils;

public class MagicLinkAuthenticator implements Authenticator {

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        String token = MagiclinkUtils.generateMagicLink(context, user);
        Boolean send = MagiclinkUtils.sendMagicLinkEmail(context.getSession(), user, token);

        System.out.println(send);

        if(send){
            context.challenge(context.form().createForm("send-email.ftl"));
        }
        context.challenge(context.form().createForm("failed-email.ftl"));
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // Handle the magic link verification
        context.success();
    }



   @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true; // Adjust as necessary for your requirements
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Set required actions if necessary
    }

    @Override
    public void close() {
        // Cleanup resources if needed
    }
}
