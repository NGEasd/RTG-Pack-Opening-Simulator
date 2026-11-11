package edu.ubb.rtgsimulator.backend.controller.filters;

import edu.ubb.rtgsimulator.backend.modell.user.Role;
import edu.ubb.rtgsimulator.backend.service.auth.AuthenticationService;
import edu.ubb.rtgsimulator.backend.service.user.UserService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class UserFilterConfig {
    private final AuthenticationService authService;
    private final UserService userService;

    @Bean
    public FilterRegistrationBean<Filter> defaultUserFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter(authService, userService, Role.DEFAULT));
        registrationBean.addUrlPatterns("/api/users/inventory", "/api/users/inventory/*", "/api/users/me",
                "/api/packs", "/api/packs/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Filter> adminUserFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserFilter(authService, userService, Role.ADMIN));
        registrationBean.addUrlPatterns("/api/players/add");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}

