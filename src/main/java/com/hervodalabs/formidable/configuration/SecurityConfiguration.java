package com.hervodalabs.formidable.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile({"h2", "mysql"})
public class SecurityConfiguration {

    // === UTENTI IN MEMORIA ===
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(

                User.builder()
                        .username("hevoda")
                        .password(passwordEncoder.encode("softwareGuru"))
                        .roles("ADMIN")
                        .build(),

                User.builder()
                        .username("kokou")
                        .password(passwordEncoder.encode("user2"))
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authz -> authz
                        // Pagine pubbliche
                        .requestMatchers("/", "/error" , "/login", "/h2-console/**").permitAll()

                        // Accesso a /products/** → ADMIN e USER
                        .requestMatchers("/products/**").hasAnyRole("ADMIN", "USER")

                        // Accesso a /projects/** → solo ADMIN
                        .requestMatchers("/projects/**").hasRole("ADMIN")

                        // Qualsiasi altra richiesta richiede login
                        .anyRequest().authenticated()
                )

                // Pagina di login customizzata
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/project/list", true)
                        .permitAll()
                )

                // Logout
                .logout(logout -> logout
                        .logoutUrl("logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // H2 console
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}
