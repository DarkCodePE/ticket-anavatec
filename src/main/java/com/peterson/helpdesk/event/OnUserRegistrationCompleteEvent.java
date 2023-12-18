package com.peterson.helpdesk.event;

import com.peterson.helpdesk.domain.Pessoa;
import com.peterson.helpdesk.security.UserSS;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.util.UriComponentsBuilder;

public class OnUserRegistrationCompleteEvent extends ApplicationEvent {
    private transient UriComponentsBuilder redirectUrl;
    private Pessoa user;

    public OnUserRegistrationCompleteEvent(Pessoa user, UriComponentsBuilder redirectUrl) {
        super(user);
        this.user = user;
        this.redirectUrl = redirectUrl;
    }

    public UriComponentsBuilder getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public Pessoa getUser() {
        return user;
    }

    public void setUser(Pessoa user) {
        this.user = user;
    }
}
