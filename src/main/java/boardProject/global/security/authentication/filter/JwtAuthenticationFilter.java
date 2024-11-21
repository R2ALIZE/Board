package boardProject.global.security.authentication.filter;

import boardProject.domain.member.entity.Member;
import boardProject.domain.member.repository.MemberRepository;
import boardProject.domain.member.service.MemberServiceHelper;
import boardProject.global.auth.dto.LoginDto;
import boardProject.global.security.authentication.handler.CustomAuthFailureHandler;
import boardProject.global.security.authentication.handler.CustomAuthSuccessHandler;
import boardProject.global.security.encryption.context.HashingContext;
import boardProject.global.security.encryption.handler.HmacSha256Hashing;
import boardProject.global.security.encryption.strategy.HashingStrategy;
import boardProject.global.security.jwt.JwtTokenService;
import boardProject.global.security.userDetail.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

import static boardProject.global.logging.LogMarkerFactory.AUTH;
import static org.springframework.http.HttpMethod.POST;

@Slf4j
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final JwtTokenService jwtTokenService;

    private final AuthenticationManager authenticationManager;

    private final MemberServiceHelper memberServiceHelper;


    public JwtAuthenticationFilter(JwtTokenService jwtTokenService,AuthenticationManager authenticationManager, MemberServiceHelper memberServiceHelper)
            throws Exception {

        super("/auth/login");
        this.jwtTokenService = jwtTokenService;
        this.authenticationManager = authenticationManager;
        this.memberServiceHelper = memberServiceHelper;

        setAuthenticationSuccessHandler(new CustomAuthSuccessHandler());
        setAuthenticationFailureHandler(new CustomAuthFailureHandler());
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {

        log.info(AUTH,"Attempt to login");

        ObjectMapper objectMapper = new ObjectMapper();
        LoginDto loginDto = objectMapper.readValue(req.getInputStream(), LoginDto.class);


        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUserEmail(), loginDto.getPassword());

        return authenticationManager.authenticate(authToken);


    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication authResult)
                                                        throws IOException, ServletException {

       Member authenticatedMember = memberServiceHelper.findSpecificMemberByEmail(((CustomUserDetails) authResult.getPrincipal()).getUsername());

        jwtTokenService.allocateJwtToResponseHeader(authenticatedMember,res);

        this.getSuccessHandler().onAuthenticationSuccess(req,res,authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                              AuthenticationException failed) throws IOException, ServletException {

        this.getFailureHandler().onAuthenticationFailure(req,res,failed);
    }
}
