package com.ssh.sustain.domain.auth;

import com.ssh.sustain.domain.Role;
import com.ssh.sustain.domain.UserTest;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

import java.util.Map;

@Getter
@Log4j
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    public static OAuthAttributes of(String registrationId, String userAttributeName, Map<String, Object> attributes) {
        if (registrationId.equals("naver")) {
            return ofNaver(userAttributeName, attributes);
        } else {
            return ofGoogle(userAttributeName, attributes);
        }
    }

    private static OAuthAttributes ofGoogle(String userAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) (attributes.get("name")))
                .email((String) (attributes.get("email")))
                .picture((String) (attributes.get("picture")))
                .attributes(attributes)
                .nameAttributeKey(userAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) (attributes.get("name")))
                .email((String) (attributes.get("email")))
                .picture((String) (attributes.get("profile_image")))
                .attributes(attributes)
                .nameAttributeKey(userAttributeName)
                .build();
    }

    public UserTest toEntity() {
        log.info(name + email + picture);
        return UserTest.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .role(Role.USER)
                .build();
    }
}
