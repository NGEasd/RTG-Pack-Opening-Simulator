package edu.ubb.rtgsimulator.backend.controller.filters;

import edu.ubb.rtgsimulator.backend.modell.user.Role;
import edu.ubb.rtgsimulator.backend.service.auth.AuthenticationService;
import edu.ubb.rtgsimulator.backend.service.user.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class UserFilter implements Filter {

    private final AuthenticationService authService;
    private final UserService userService;
    private final Role requiredRole;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String authHeader = httpRequest.getHeader("Authorization");

        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:5173");


        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }

        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String jwtToken = authHeader.substring(7);
                String userID = authService.extractUserID(jwtToken);

                // user kinyerese
                if (userID == null || !authService.isTokenValid(jwtToken, userID)) {
                    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    chain.doFilter(request, response);
                    return;
                }

                // jogosultsagellenorzes
                if (!haveEntry(userService.getUserByID(Long.parseLong(userID)).getRole(), requiredRole)) {
                    ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_FORBIDDEN);
                    chain.doFilter(request, response);
                    return;
                }

                // csak ha nincs! attribute
                if (httpRequest.getAttribute("userID") == null) {
                    httpRequest.setAttribute("userID", userID);
                    System.out.println("User ID property set: " + userID);
                    chain.doFilter(request, response);
                }
            } else {
                log.info("Auth header wit Bearer not found!");
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (ExpiredJwtException e) {
            log.info("User dont have JWT, or JWT expired!");
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    private boolean haveEntry(Role role, Role requiredRole) {
        if (role == Role.ADMIN) return true;
        else return (role == requiredRole);
    }
}