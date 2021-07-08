package com.ssh.sustain.security.social;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Social {

    private final String clientId;

    private final String clientSecret;

    private final String callback;

    private final String apiEndpoint;

    @Builder
    public Social(String clientId, String clientSecret, String callback, String apiEndpoint) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.callback = callback;
        this.apiEndpoint = apiEndpoint;
    }
}
