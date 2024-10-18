package boardProject.global.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrfConfig ->
                        csrfConfig.disable())

                .cors(corsConfig ->
                        corsConfig.disable())

                .formLogin(formLoginConfig ->
                        formLoginConfig.disable())

                .httpBasic( httpBasicConfig ->
                        httpBasicConfig.disable())

                .authorizeHttpRequests( auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/member/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/article/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/comment/**")).permitAll()
                        .anyRequest().permitAll()
                );


        return http.build();

    }






}
