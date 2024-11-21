package boardProject.global.security.authorization.filter;

import boardProject.domain.member.service.MemberServiceHelper;
import boardProject.global.security.jwt.JwtTokenService;
import boardProject.global.util.authority.CustomAuthorityUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static boardProject.global.logging.LogMarkerFactory.AUTH;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final MemberServiceHelper memberServiceHelper;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {


        if (request.getMethod().equals("OPTIONS") || request.getHeader("Authorization") == null) {
            filterChain.doFilter(request,response);
            return;
        }

        try {

            log.info(AUTH, "JwtAuthorizationFilter");

            String accessToken = jwtTokenService.extractAccessToken(request);
            Claims claims = jwtTokenService.validateJwtToken(accessToken);
            setAuthenticationToContext(claims);

        } catch (Exception e) {
            log.error("Exception Occurs during authorization checking : {}",e.getMessage());
        }

        filterChain.doFilter(request,response);
    }




    private void setAuthenticationToContext(Map<String,Object> claims) {

        Long memberId = ((Integer) claims.get("memberId")).longValue();

        String userEmail = memberServiceHelper.findSpecificMemberById(memberId).getEmail();

        List authorities = (List) CustomAuthorityUtil.authorities();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userEmail,null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }


}
