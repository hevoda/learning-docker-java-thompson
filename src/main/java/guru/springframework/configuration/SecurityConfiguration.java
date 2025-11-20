package guru.springframework.configuration;

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
@Profile({"h2", "dev", "mongo"})
public class SecurityConfiguration {

    // === UTENTI IN MEMORIA ===
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(

                User.builder()
                        .username("hervoda")
                        .password(passwordEncoder.encode("softwareGuru"))
                        .roles("ADMIN")
                        .build(),

                User.builder()
                        .username("herve")
                        .password(passwordEncoder.encode("admin"))
                        .roles("USER")
                        .build(),

                User.builder()
                        .username("kokou")
                        .password(passwordEncoder.encode("user"))
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

                        // Dichiarazione pagine pubbliche
                        .requestMatchers("/", "/login", "/h2-console/**").permitAll()

                        // API solo ADMIN
                        .requestMatchers("/api/products/**").hasRole("ADMIN")

                        // Tutte le rotte /product/ richiedono USER o ADMIN
                        .requestMatchers("/product/**").hasAnyRole("USER", "ADMIN")

                        // Qualsiasi altra richiesta richiede login
                        .anyRequest().authenticated()
                )

                // Pagina di login customizzata
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )

                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // H2 console
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}
