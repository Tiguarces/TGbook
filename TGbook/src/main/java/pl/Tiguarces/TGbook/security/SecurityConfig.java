package pl.Tiguarces.TGbook.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import tiguarces.PasswD;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final SecurityHelper helper;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .exceptionHandling()
                .accessDeniedHandler(helper::handleDeniedHandler);

        httpSecurity
                .authorizeHttpRequests(helper::authorizeRequests);

        httpSecurity
                .csrf()
                .disable();

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(final CharSequence rawPassword) {
                return PasswD.encode(rawPassword.toString());
            }

            @Override
            public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
                return PasswD.match(encodedPassword, rawPassword.toString());
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authentication) throws Exception {
        return authentication.getAuthenticationManager();
    }
}
