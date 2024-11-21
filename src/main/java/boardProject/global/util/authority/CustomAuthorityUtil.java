package boardProject.global.util.authority;

import boardProject.domain.member.entity.Member;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class CustomAuthorityUtil {


    public static Collection<? extends GrantedAuthority> rolesToAuthorities(Member member) {

    return member.getRoles().stream()
                     .map(
                             role -> new SimpleGrantedAuthority("ROLE_" + role)
                     )
                     .toList();

    }


//    public static Collection<? extends GrantedAuthority> createAuthoritiesFrom(Member member) {
//
//        return rolesToAuthorities(member.getRoles());
//
//    }


    public static Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }




}
