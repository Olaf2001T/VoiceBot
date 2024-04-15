package com.auth0.example.Controller;

import com.auth0.example.Entity.User;
import com.auth0.example.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @Value("${okta.oauth2.client-id}")
    private String clientId;
    UserService userService;

    @Autowired
    public ApplicationController(UserService userService) {
        this.userService = userService;
    }

    public String userId;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        return "start";
    }

    @GetMapping("/main")
    public String mainPage(Model model, @AuthenticationPrincipal OidcUser principal) {
        model.addAttribute("id", clientId);
        if (principal != null) {
            userId = principal.getSubject();
            if (!userService.existsByUsername(userId)) {
                User user = new User();
                user.setAuthId(userId);
                userService.updateUser(user);
            }
        }
        return "pageMain";
    }
}
