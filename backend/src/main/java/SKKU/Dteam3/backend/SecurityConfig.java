package SKKU.Dteam3.backend;

import SKKU.Dteam3.backend.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final AuthenticationEntryPoint entryPoint;

        @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(
                        csrf -> csrf.disable()
                )
                .authorizeHttpRequests(
                        authorizeRequests -> authorizeRequests
                                .requestMatchers("/oauth/**").permitAll()
                                .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
//                .exceptionHandling(
//                        handler -> handler.authenticationEntryPoint(entryPoint)
//                ) // TODO : fix exception handling
                .sessionManagement(
                        sessionManagement -> sessionManagement.disable()
                )
                .formLogin(
                        formLogin -> formLogin.disable()
                )
                .httpBasic(
                        httpBasic -> httpBasic.disable()
                )
                .headers(
                        headers -> headers.frameOptions(
                                frameOptions -> frameOptions.disable()
                        )
                );

        return httpSecurity.build();
    }
}
