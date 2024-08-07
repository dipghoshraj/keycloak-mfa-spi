package com.mfa.magiclink;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.keycloak.email.EmailException;
import org.keycloak.email.EmailTemplateProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import org.keycloak.models.UserModel;


public class MagiclinkUtils {
    
    private static final String SECRET_KEY = "your-secret-key"; // Replace with a secure key
    private static final long EXPIRATION_TIME = 600_000; // 10 minutes

    public static String generateMagicLink(UserModel user) {
        String token = Jwts.builder()
                .setSubject(user.getId())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

        String magicLink = "https://localhost:8000/auth/realms/your-realm/protocol/openid-connect/auth?token=" + token;
        return magicLink;
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
            return false;
        }
    }
}
