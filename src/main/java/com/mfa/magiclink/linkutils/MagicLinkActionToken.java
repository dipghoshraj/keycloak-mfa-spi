package com.mfa.magiclink.linkutils;

import org.keycloak.authentication.actiontoken.DefaultActionToken;

public class MagicLinkActionToken extends DefaultActionToken {

    public static final String TOKEN_TYPE = "magic-link";

    @SuppressWarnings("unused")
    private MagicLinkActionToken() {
    }

    public MagicLinkActionToken(String userId, int absoluteExpirationInSecs, String email, String clientId) {
        super(userId, TOKEN_TYPE, absoluteExpirationInSecs, null);
        this.issuedFor = clientId;
        this.setOtherClaims("email", email);
    }

    @Override
    public String getActionId() {
        return TOKEN_TYPE;
    }
}