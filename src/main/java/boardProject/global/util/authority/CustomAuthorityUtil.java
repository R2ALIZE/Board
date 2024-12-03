package boardProject.global.util.authority;

import boardProject.domain.member.entity.Member;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

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



    public static Collection<? extends GrantedAuthority> authorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }




}
