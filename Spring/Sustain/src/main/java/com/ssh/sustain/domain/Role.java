package com.ssh.sustain.domain;

import com.ssh.sustain.mapper.CodeEnumString;
import com.ssh.sustain.mapper.CodeEnumStringHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.type.MappedTypes;

@Getter
@RequiredArgsConstructor
public enum Role implements CodeEnumString {

    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    EXPERT("ROLE_EXPERT");

    private final String key;

    @MappedTypes(Role.class)
    public static class TypeHandler extends CodeEnumStringHandler<Role> {
        public TypeHandler() {
            super(Role.class);
        }
    }

    @Override
    public String getCode() {
        return key;
    }

}
