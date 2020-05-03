package com.VU.PSKProject.Constants;

public class WebConstants {

    private WebConstants() {}

    public static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/swagger-ui**",
            "/swagger-ui/**",
            "/webjars/**",
            "/h2-console/**",
            "/favicon.ico"
            // other public endpoints of your API may be appended to this array
    };
}
