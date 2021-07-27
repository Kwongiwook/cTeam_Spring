package com.ssh.sustain.security.auth.user.oauth2;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * CustomOAuth2User의 Default Implementation. email이 user identify 목적으로 쓰이기에 nameAttributeKey 또한 email이다.
 * uid 역시 unique하기에 candidate key로써 기능할 수 있지만 social user가 아닌 경우를 고려해서 email을 사용한다.
 */
public class DefaultCustomOAuth2User implements CustomOAuth2User, Serializable {
    private static final long serialVersionUID = 1L;

    private final Set<GrantedAuthority> authorities;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;

    public DefaultCustomOAuth2User(Set<GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey) {
        if (!attributes.containsKey(nameAttributeKey)) {
            throw new IllegalArgumentException("Missing attribute '" + nameAttributeKey + "' in attributes");
        }

        this.authorities = authorities;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
    }

    /**
     * @return social login response에 포함된 "ID"를 리턴한다.
     */
    @Override
    public String getUID() {
        return this.getAttributes().get("sub").toString();
    }

    @Override
    public String getNickName() {
        return this.getAttributes().get("name").toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    /**
     * @return getEmail()과 동일하다고 볼 수 있음.
     */
    @Override
    public String getName() {
        return this.getAttributes().get(nameAttributeKey).toString();
    }

    @Override
    public String getAuth() {
        String role = null;
        for (GrantedAuthority authority : this.getAuthorities()) {
            role = authority.getAuthority();
        }
        return role;
    }
}
