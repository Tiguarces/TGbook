package pl.Tiguarces.TGbook.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
class SecurityHelper {

    private final String[] allowedRoutes;

    public SecurityHelper() {
        allowedRoutes = new String[] {
                "/auth/login", "/auth/register", "/auth/logout",
                "/auth/fetch/by-nickname", "/auth/activate",
                "/book/fetch/all", "/book/register"
        };
    }

    public void handleDeniedHandler(final HttpServletRequest request, final HttpServletResponse response, final AccessDeniedException accessDeniedException) {
        response.setStatus(403);
    }

    public void authorizeRequests(final AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authorization) {
        authorization
                .antMatchers(allowedRoutes)
                .permitAll();

        authorization
                .anyRequest()
                .authenticated();
    }
}
