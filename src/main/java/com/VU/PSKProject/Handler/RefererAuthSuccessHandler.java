package com.VU.PSKProject.Handler;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class RefererAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    public RefererAuthSuccessHandler(){
        super();
        setUseReferer(true);
    }
}
