package boardProject.global.security.config;

import boardProject.domain.member.repository.MemberRepository;
import boardProject.domain.member.service.MemberServiceHelper;
import boardProject.global.security.authentication.entryPoint.CustomAuthenticationEntryPoint;
import boardProject.global.security.authentication.filter.JwtAuthenticationFilter;
import boardProject.global.security.authorization.filter.JwtAuthorizationFilter;
import boardProject.global.security.authorization.handler.CustomAccessDeniedHandler;
import boardProject.global.security.jwt.JwtTokenService;
import boardProject.global.security.userDetail.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {

    private final JwtTokenService jwtTokenService;

    private final MemberServiceHelper memberServiceHelper;

    private final MemberRepository memberRepository;



    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authenticationProvider);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CustomUserDetailsService userDetailsService() {
        return new CustomUserDetailsService(memberRepository);
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();

        // 허용할 Origins (도메인)
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "OPTIONS"));

        // 허용할 요청 헤더
        configuration.setAllowedHeaders(List.of("*"));

        // 클라이언트가 접근할 수 있는 응답 헤더
        configuration.setExposedHeaders(List.of("*"));

        // 자격 증명을 포함할 수 있도록 설정
        configuration.setAllowCredentials(true);

        var source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 CORS 설정을 적용
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry
//                .addInterceptor(new JwtParseInterceptor(jwtTokenService))
//                .addPathPatterns("/**");
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .headers(headers -> headers
                        .frameOptions(
                                HeadersConfigurer.FrameOptionsConfig::sameOrigin
                        )
                )

                .csrf(AbstractHttpConfigurer::disable)

                .cors(cors-> cors
                        .configurationSource(corsConfigurationSource())
                )

                .formLogin(AbstractHttpConfigurer::disable)

                .logout(AbstractHttpConfigurer::disable)

                .httpBasic(AbstractHttpConfigurer::disable)

                .sessionManagement(session -> session
                        .sessionCreationPolicy(STATELESS)
                )

                .exceptionHandling(auth -> auth
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

                .authorizeHttpRequests(path -> path

                        // PREFLIGHT REQUEST
                        .requestMatchers(OPTIONS,"/**").permitAll()

                        // AUTH
                        .requestMatchers(POST,"/auth/registration").permitAll()
                        .requestMatchers(POST,"/auth/login").permitAll()
                        .requestMatchers(POST,"/auth/refresh").hasRole("USER")
                        .requestMatchers(POST,"/auth/logout").hasRole("USER")
                        .requestMatchers(POST,"/auth/withdrawal").hasRole("USER")

                        // MEMBER
                        .requestMatchers(GET,"/member/info/{member-id}").hasAnyRole("USER","ADMIN")
                        .requestMatchers(PATCH,"/member/info/modification/{member-id}").hasRole("USER")

                        // ARTICLE
                        .requestMatchers(GET,"/article/**").permitAll()
                        .requestMatchers(POST,"/article").hasAnyRole("USER","ADMIN")
                        .requestMatchers(PATCH,"/article/{article-id}").hasRole("USER")
                        .requestMatchers(DELETE,"/article/{article-id}").hasAnyRole("USER","ADMIN")

                        // COMMENT
                        .requestMatchers(GET,"/comment/**").permitAll()
                        .requestMatchers(POST,"/comment").hasAnyRole("USER","ADMIN")
                        .requestMatchers(PATCH,"/comment/{comment-id}").hasAnyRole("USER","ADMIN")
                        .requestMatchers(DELETE,"/comment/{comment-id}").hasAnyRole("USER","ADMIN")

                        .anyRequest().authenticated()

                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenService,authenticationManager(),memberServiceHelper), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtAuthorizationFilter(jwtTokenService,memberServiceHelper), JwtAuthenticationFilter.class)

        ;

        return http.build();

    }
}
