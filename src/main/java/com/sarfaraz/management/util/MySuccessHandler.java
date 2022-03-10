package com.sarfaraz.management.util;

import com.sarfaraz.management.model.User;
import com.sarfaraz.management.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;/*
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;*/
import org.springframework.stereotype.Component;
/*
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;
*/
/*
@Component("myAuthenticationSuccessHandler")
public class MySuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    UserService userService;
    private final Logger log = LoggerFactory.getLogger(MySuccessHandler.class);

    @Autowired
    public MySuccessHandler(UserService userService) {
        this.userService = userService;
        setUseReferer(true);
    }
    */
/*
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String url = "/user/profile";

        HttpSession session = request.getSession(false);
        if (session != null) {
            Optional<User> opt = userService.findByEmail(authentication.getName());
            opt.ifPresent(u -> session.setAttribute("loggedUser", u));
        }
        if (response.isCommitted()) return;
        RedirectStrategy strategy = new DefaultRedirectStrategy();
        strategy.sendRedirect(request, response, url);
        log.info(request.getRequestURI());
    }
}*/