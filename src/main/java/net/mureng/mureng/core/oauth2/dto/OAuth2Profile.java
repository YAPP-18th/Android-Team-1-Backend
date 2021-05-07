package net.mureng.mureng.core.oauth2.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OAuth2Profile {
    private final String email;

    public String getEmail(){
        return email;
    };
}
