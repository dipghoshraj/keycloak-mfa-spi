package com.mfa.magiclink.linkutils;

import org.keycloak.authentication.actiontoken.DefaultActionToken;

import com.fasterxml.jackson.annotation.JsonProperty;
public class MagicLinkActionToken extends DefaultActionToken {

    public static final String TOKEN_TYPE = "magic-link";
    private static final String JSON_FIELD_REDIRECT_URI = "rdu";

    @JsonProperty(value = JSON_FIELD_REDIRECT_URI)
    private String redirectUri;


    @SuppressWarnings("unused")
    private MagicLinkActionToken() {
    }

    public MagicLinkActionToken(String userId, int absoluteExpirationInSecs, String email, String clientId, String redirectUri) {
        super(userId, TOKEN_TYPE, absoluteExpirationInSecs, null);
        this.issuedFor = clientId;
        this.redirectUri = redirectUri;
        this.setOtherClaims("email", email);
    }

    @Override
    public String getActionId() {
        return TOKEN_TYPE;
    }
}