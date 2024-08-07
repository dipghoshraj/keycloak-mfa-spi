package com.mfa.magiclink;

import org.keycloak.Config;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.AuthenticatorFactory;
import org.keycloak.models.AuthenticationExecutionModel.Requirement;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;

import java.util.ArrayList;
import java.util.List;


public class MagicLinkAuthenticatorFactory implements AuthenticatorFactory {
    
    public static final String PROVIDER_ID = "magiclink-authenticator";
    private static final List<ProviderConfigProperty> CONFIG_PROPERTIES = new ArrayList<>();


    @Override
    public Authenticator create(KeycloakSession session) {
        return new MagicLinkAuthenticator();
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public String getHelpText() {
        return "Authenticator for Magic Link";
    }

    @Override
    public String getDisplayType() {
        return "Magic Link Authenticator";
    }

    @Override
    public Requirement[] getRequirementChoices() {
        return new Requirement[]{Requirement.REQUIRED};
    }

    @Override
    public String getReferenceCategory() {
        return "Magic Link";
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    @Override
    public boolean isUserSetupAllowed() {
        return false;
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return CONFIG_PROPERTIES;
    }
}
