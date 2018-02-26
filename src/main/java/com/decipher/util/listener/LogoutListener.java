package com.decipher.util.listener;

import com.decipher.agriculture.data.account.Account;
import com.decipher.agriculture.service.account.AccountService;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Abhishek Samuel
 */
@Component
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

    private AccountService accountService;

    @Inject
    public LogoutListener(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void onApplicationEvent(SessionDestroyedEvent sessionDestroyedEvent) {

        List<SecurityContext> lstSecurityContext = sessionDestroyedEvent.getSecurityContexts();

        for (SecurityContext securityContext : lstSecurityContext) {
            UserDetails ud = (UserDetails) securityContext.getAuthentication().getPrincipal();
            Account account = accountService.getEnabledUserByEmail(ud.getUsername());
            account.setLastActiveTime(DateTime.now().getMillis());
            accountService.updateUser(account);
        }
    }
}
