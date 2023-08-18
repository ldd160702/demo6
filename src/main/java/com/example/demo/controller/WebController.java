package com.example.demo.controller;



import com.example.demo.model.UserEntity;
import com.example.demo.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
public class WebController {
    final UserDetailsServiceImpl userService;


    @RequestMapping(value = {"/", "/home", "/index"})
    public ModelAndView homepage(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            UserEntity user = userService.getUserByUsername(authentication.getName());
            if (user != null) {
                if (user.getRoles()[0].equals("ROLE_ADMIN")) {
                    return new ModelAndView("redirect:/admin");
                } else if (user.getRoles()[0].equals("ROLE_USER")) {
                    return new ModelAndView("redirect:/user");
                }
            }
        }

        return new ModelAndView("index"); // Trả về index.jsp
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView userPage() {
        return new ModelAndView("user");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        return new ModelAndView("admin");
    }


    @RequestMapping(value = "/sendContactMail", method = RequestMethod.POST)
    public ModelAndView sendMail() {
        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/login-page", method = RequestMethod.GET)
    public ModelAndView login() {
        return new ModelAndView("login-page");
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            //sessionService.removeAttribute("loggedInUser");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login-page?logout"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

    /*public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }*/
}
